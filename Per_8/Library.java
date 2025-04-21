package Perpus;
import java.util.*;

// Interface dasar untuk item yang dapat dipinjam
interface Borrowable<T> {
    String getId();
    String getTitle();
    boolean isAvailable();
    void setBorrowed(boolean status);
    int getLoanPeriod(); // Periode pinjam dalam hari
}

// Class generic untuk pengelolaan perpustakaan
class Library<T extends Borrowable<T>> {
    private List<T> items;
    
    public Library() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(T item) {
        items.add(item);
    }
    
    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }
    
    public T findItem(String id) {
        for (T item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    
    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }
    
    public List<T> getAvailableItems() {
        List<T> availableItems = new ArrayList<>();
        for (T item : items) {
            if (item.isAvailable()) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }
    
    public boolean borrowItem(String id) {
        T item = findItem(id);
        if (item != null && item.isAvailable()) {
            item.setBorrowed(true);
            return true;
        }
        return false;
    }
    
    public boolean returnItem(String id) {
        T item = findItem(id);
        if (item != null && !item.isAvailable()) {
            item.setBorrowed(false);
            return true;
        }
        return false;
    }
}

// Class abstak untuk semua item perpustakaan
abstract class LibraryItem implements Borrowable<LibraryItem> {
    private String id;
    private String title;
    private boolean available;
    
    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.available = true;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public boolean isAvailable() {
        return available;
    }
    
    @Override
    public void setBorrowed(boolean status) {
        this.available = !status;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Judul: " + title + ", Status: " + (available ? "Tersedia" : "Dipinjam");
    }
}

// Class untuk merepresentasikan buku
class Book extends LibraryItem {
    private String author;
    private String genre;
    private int pages;
    private int yearPublished;
    
    public Book(String id, String title, String author, String genre, int pages, int yearPublished) {
        super(id, title);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.yearPublished = yearPublished;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public int getPages() {
        return pages;
    }
    
    public int getYearPublished() {
        return yearPublished;
    }
    
    @Override
    public int getLoanPeriod() {
        return 14; // Periode peminjaman buku 14 hari
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Penulis: " + author + ", Genre: " + genre + 
               ", Halaman: " + pages + ", Tahun Terbit: " + yearPublished;
    }
}

// Class untuk merepresentasikan majalah
class Magazine extends LibraryItem {
    private String publisher;
    private int issueNumber;
    private String releaseDate;
    
    public Magazine(String id, String title, String publisher, int issueNumber, String releaseDate) {
        super(id, title);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
        this.releaseDate = releaseDate;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public int getIssueNumber() {
        return issueNumber;
    }
    
    public String getReleaseDate() {
        return releaseDate;
    }
    
    @Override
    public int getLoanPeriod() {
        return 7; // Periode peminjaman majalah 7 hari
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Penerbit: " + publisher + 
               ", No. Edisi: " + issueNumber + ", Tanggal Terbit: " + releaseDate;
    }
}
