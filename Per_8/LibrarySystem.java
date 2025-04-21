package Perpus;
import java.util.*;

// Main class untuk aplikasi perpustakaan
public class LibrarySystem {
    private static Library<LibraryItem> library = new Library<>();
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        initializeLibrary();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Pilih menu: ");
            
            switch (choice) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    displayAvailableBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    addNewBook();
                    break;
                case 7:
                    removeBook();
                    break;
                case 8:
                    addNewUser();
                    break;
                case 9:
                    displayUsers();
                    break;
                case 0:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan Sistem Perpustakaan!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        scanner.close();
    }
    
    private static void initializeLibrary() {
        // Tambahkan buku awal
        library.addItem(new Book("B001", "Java Programming", "John Smith", "Programming", 450, 2020));
        library.addItem(new Book("B002", "Data Structures and Algorithms", "Jane Doe", "Computer Science", 380, 2019));
        library.addItem(new Book("B003", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 180, 1925));
        library.addItem(new Book("B004", "To Kill a Mockingbird", "Harper Lee", "Fiction", 281, 1960));
        library.addItem(new Book("B005", "Clean Code", "Robert C. Martin", "Programming", 464, 2008));
        library.addItem(new Magazine("M001", "National Geographic", "National Geographic Society", 245, "January 2023"));
        
        // Tambahkan pengguna awal
        users.add(new User("U001", "Ahmad"));
        users.add(new User("U002", "Budi"));
    }
    
    private static void displayMenu() {
        System.out.println("\n===== SISTEM MANAJEMEN PERPUSTAKAAN =====");
        System.out.println("1. Tampilkan Semua Buku");
        System.out.println("2. Tampilkan Buku yang Tersedia");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Tambah Buku Baru");
        System.out.println("7. Hapus Buku");
        System.out.println("8. Tambah Pengguna Baru");
        System.out.println("9. Tampilkan Semua Pengguna");
        System.out.println("0. Keluar");
    }
    
    private static void displayAllBooks() {
        List<LibraryItem> items = library.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Tidak ada buku dalam perpustakaan.");
            return;
        }
        
        System.out.println("\n===== DAFTAR SEMUA BUKU =====");
        for (LibraryItem item : items) {
            System.out.println(item);
        }
    }
    
    private static void displayAvailableBooks() {
        List<LibraryItem> availableItems = library.getAvailableItems();
        if (availableItems.isEmpty()) {
            System.out.println("Tidak ada buku yang tersedia untuk dipinjam.");
            return;
        }
        
        System.out.println("\n===== DAFTAR BUKU TERSEDIA =====");
        for (LibraryItem item : availableItems) {
            System.out.println(item);
        }
    }
    
    private static void searchBook() {
        System.out.print("Masukkan ID buku atau judul buku: ");
        String keyword = scanner.nextLine();
        
        boolean found = false;
        for (LibraryItem item : library.getAllItems()) {
            if (item.getId().equalsIgnoreCase(keyword) || item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                if (!found) {
                    System.out.println("\n===== HASIL PENCARIAN =====");
                    found = true;
                }
                System.out.println(item);
            }
        }
        
        if (!found) {
            System.out.println("Buku tidak ditemukan.");
        }
    }
    
    private static void borrowBook() {
        displayAvailableBooks();
        System.out.print("Masukkan ID buku yang ingin dipinjam: ");
        String bookId = scanner.nextLine();
        
        System.out.print("Masukkan ID pengguna: ");
        String userId = scanner.nextLine();
        
        LibraryItem book = library.findItem(bookId);
        User user = findUser(userId);
        
        if (book == null) {
            System.out.println("Buku dengan ID " + bookId + " tidak ditemukan.");
            return;
        }
        
        if (user == null) {
            System.out.println("Pengguna dengan ID " + userId + " tidak ditemukan.");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Buku ini sudah dipinjam.");
            return;
        }
        
        user.borrowItem(book);
        System.out.println("Buku berhasil dipinjam oleh " + user.getName() + ".");
    }
    
    private static void returnBook() {
        System.out.print("Masukkan ID pengguna: ");
        String userId = scanner.nextLine();
        
        User user = findUser(userId);
        if (user == null) {
            System.out.println("Pengguna dengan ID " + userId + " tidak ditemukan.");
            return;
        }
        
        List<LibraryItem> borrowedItems = user.getBorrowedItems();
        if (borrowedItems.isEmpty()) {
            System.out.println("Pengguna " + user.getName() + " tidak memiliki buku yang dipinjam.");
            return;
        }
        
        System.out.println("\n===== BUKU YANG DIPINJAM =====");
        for (int i = 0; i < borrowedItems.size(); i++) {
            System.out.println((i + 1) + ". " + borrowedItems.get(i));
        }
        
        int bookIndex = getIntInput("Pilih nomor buku yang ingin dikembalikan: ") - 1;
        if (bookIndex < 0 || bookIndex >= borrowedItems.size()) {
            System.out.println("Nomor buku tidak valid.");
            return;
        }
        
        LibraryItem bookToReturn = borrowedItems.get(bookIndex);
        user.returnItem(bookToReturn);
        System.out.println("Buku " + bookToReturn.getTitle() + " berhasil dikembalikan.");
    }
    
    private static void addNewBook() {
        System.out.println("\n===== TAMBAH BUKU BARU =====");
        System.out.print("Masukkan ID buku: ");
        String id = scanner.nextLine();
        
        // Check if ID already exists
        if (library.findItem(id) != null) {
            System.out.println("ID buku sudah digunakan. Silakan gunakan ID lain.");
            return;
        }
        
        System.out.print("Masukkan judul buku: ");
        String title = scanner.nextLine();
        
        System.out.print("Pilih jenis item (1: Buku, 2: Majalah): ");
        int itemType = getIntInput("");
        
        if (itemType == 1) {
            System.out.print("Masukkan nama penulis: ");
            String author = scanner.nextLine();
            
            System.out.print("Masukkan genre: ");
            String genre = scanner.nextLine();
            
            int pages = getIntInput("Masukkan jumlah halaman: ");
            int year = getIntInput("Masukkan tahun terbit: ");
            
            Book newBook = new Book(id, title, author, genre, pages, year);
            library.addItem(newBook);
            System.out.println("Buku baru berhasil ditambahkan.");
        } else if (itemType == 2) {
            System.out.print("Masukkan nama penerbit: ");
            String publisher = scanner.nextLine();
            
            int issueNumber = getIntInput("Masukkan nomor edisi: ");
            
            System.out.print("Masukkan tanggal terbit: ");
            String releaseDate = scanner.nextLine();
            
            Magazine newMagazine = new Magazine(id, title, publisher, issueNumber, releaseDate);
            library.addItem(newMagazine);
            System.out.println("Majalah baru berhasil ditambahkan.");
        } else {
            System.out.println("Jenis item tidak valid.");
        }
    }
    
    private static void removeBook() {
        displayAllBooks();
        System.out.print("Masukkan ID buku yang ingin dihapus: ");
        String id = scanner.nextLine();
        
        LibraryItem item = library.findItem(id);
        if (item == null) {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return;
        }
        
        if (!item.isAvailable()) {
            System.out.println("Buku sedang dipinjam. Tidak dapat dihapus.");
            return;
        }
        
        library.removeItem(id);
        System.out.println("Buku berhasil dihapus dari perpustakaan.");
    }
    
    private static void addNewUser() {
        System.out.println("\n===== TAMBAH PENGGUNA BARU =====");
        System.out.print("Masukkan ID pengguna: ");
        String id = scanner.nextLine();
        
        // Check if ID already exists
        if (findUser(id) != null) {
            System.out.println("ID pengguna sudah digunakan. Silakan gunakan ID lain.");
            return;
        }
        
        System.out.print("Masukkan nama pengguna: ");
        String name = scanner.nextLine();
        
        User newUser = new User(id, name);
        users.add(newUser);
        System.out.println("Pengguna baru berhasil ditambahkan.");
    }
    
    private static void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("Tidak ada pengguna terdaftar.");
            return;
        }
        
        System.out.println("\n===== DAFTAR PENGGUNA =====");
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    private static User findUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Masukkan angka yang valid: ");
            }
        }
    }
}
