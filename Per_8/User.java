package Perpus;
import java.util.*;


// Class untuk pengguna perpustakaan
class User {
    private String id;
    private String name;
    private List<LibraryItem> borrowedItems;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void borrowItem(LibraryItem item) {
        if (item.isAvailable()) {
            item.setBorrowed(true);
            borrowedItems.add(item);
        }
    }
    
    public void returnItem(LibraryItem item) {
        if (borrowedItems.contains(item)) {
            item.setBorrowed(false);
            borrowedItems.remove(item);
        }
    }
    
    public List<LibraryItem> getBorrowedItems() {
        return new ArrayList<>(borrowedItems);
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + name + ", Jumlah Buku Dipinjam: " + borrowedItems.size();
    }
}
