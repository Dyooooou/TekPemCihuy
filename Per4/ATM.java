import java.io.*; //biar bisa baca/read file
import java.util.*; //buat scanner, array, list

// Interface untuk operasi rekening
interface AccountOperations { //deklarasi method, isinya body kosong (di c kaya dekalrasi)
    void checkBalance();
    void deposit(double amount);
    void transfer(double amount);
}

// Abstract superclass untuk transaksi
abstract class Transaction { //abstact class, jadi superclass untuk semua transaksi
    protected String accountNumber; //protected biar bisa di pake subclass lain

    public Transaction(String accountNumber) { //konstruktor untuk init account number
        this.accountNumber = accountNumber; //nyimpan nilai 
    }

    public abstract void execute(); // Metode abstrak yang akan diimplementasikan subclass
}

// Kelas rekening untuk menyimpan informasi saldo dalam file
class Account implements AccountOperations { //class account, akses interface dgn Implement
    private String accountNumber; //atributes nyimpen info rekening private biar hanya subclass ini bs akses
    private String name;
    private double balance;
    private String pin;

    // konstructor untuk membuat rekening baru
    public Account(String accountNumber, String name, double balance, String pin) { //konstruktor buat bikin rek baru
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
        //this. digunakan untuk membedakan antara variabel instance dan parameter dengan nama yang sama
    }

    public String getPin() { //getter buat ambil pin rek
        return pin;
    }

    // Metode untuk memuat rekening dari file
    public static Account loadFromFile(String accountNumber) { //load file, kata kuncinya no rekening
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) { //coba baca file, 
            String line; //baca per baris trs simpan
                while ((line = reader.readLine()) != null) { // Baca setiap baris dalam file
                String[] data = line.split(","); // Pisahkan data dengan koma
                
                if (data[0].equals(accountNumber)) { // Jika cocokin no rekening 
                    return new Account(data[0], data[1], Double.parseDouble(data[2]), data[3]); // Buat objek Account
                }
            }
            }
        } catch (IOException e) { //exception 
            System.out.println("Gagal membaca file akun.");
        }
        return null; // Jika rekening tidak ditemukan, kembalikan null
    }

    // Simpan rekening ke dalam file jika belum ada
    public void saveToFile() { //method 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) { //tulis file ke txt, mode append
            writer.write(accountNumber + "," + name + "," + balance + "," + pin); //tulis rek, nama, saldo, pin di pisah koma
            writer.newLine(); //buat baris baru
        } catch (IOException e) {
            System.out.println("Gagal menyimpan rekening ke file.");
        }
    }

    // Metode untuk mengecek saldo
    public void checkBalance() { //method
        System.out.println("Saldo saat ini: Rp " + balance); //print saldo berdasarkan file
    }

    // Metode untuk menyetor uang ke rekening
    public void deposit(double amount) { //input ammount
        if (amount > 0) {
            balance += amount; // Tambah saldo dgn jumlah yg disetor sblumnya
            updateAccount(); // Perbarui data di file
            System.out.println("Setor berhasil. Saldo sekarang: Rp " + balance); //saldo baru stlh ditmbah
        } else {
            System.out.println("Jumlah setor harus lebih dari 0.");
        }
    }

    // Metode untuk transfer uang 
    public void transfer(double amount) { //input amount
        if (amount > 0 && amount <= balance) {
            balance -= amount; // Kurangi saldo dgn jumlah ammount
            updateAccount(); // Perbarui saldo di file
            System.out.println("Transfer berhasil. Saldo sekarang: Rp " + balance); //saldo baru stlh dikurang
        } else {
            System.out.println("Saldo tidak cukup atau jumlah tidak valid.");
        }
    }

    // Metode untuk memperbarui saldo rekening dalam file
    private void updateAccount() { //ngubah data di txt 
        List<String> lines = new ArrayList<>(); //init buat nyimpen data baru (update)
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) { //baca file
            String line; //setiap baris
            while ((line = reader.readLine()) != null) { //loop baca tiap baris
                String[] data = line.split(","); //pisah data pake , (koma)
                if (data[0].equals(this.accountNumber)) { //cek kesesuaian no rek
                    lines.add(accountNumber + "," + name + "," + balance + "," + pin); //bakal tulis yang baru
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file akun.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) { //tulis ulang file
            for (String l : lines) { //baca tiap line sbnyk data, dari ArrayList
                writer.write(l); //tulis ulang
                writer.newLine(); //\n biar rapi dikit
            }
        } catch (IOException e) {
            System.out.println("Gagal memperbarui akun.");
        }
    }
}

// Subclass transaksi setor tunai
class Deposit extends Transaction { //inherit dari tranxaction
    private double amount; //dekalr ammount private

    public Deposit(String accountNumber, double amount) { //konstruktor buat setor tunai
        super(accountNumber); //panggil konstruktor cuperclass dari Transaction
        this.amount = amount; //init ammount baru tanpa bikin objek
    }

    @Override //override method buat setor tunai
    public void execute() { //method execute
        Account account = Account.loadFromFile(accountNumber); //load file
        if (account != null) { //mengecek file ada/ tidak
            account.deposit(amount); //method deposit dari class account sbnyk ammount
        } else {
            System.out.println("Rekening tidak ditemukan.");
        }
    }
}

// Subclass transaksi transfer
class Transfer extends Transaction { //inherit dari transaction
    private double amount; //deklar ammount private

    public Transfer(String accountNumber, double amount) { //konstruktor buat transfer
        super(accountNumber); //panggil konstruktor superclass dari transcaction
        this.amount = amount; //ammount baru tanpa deklar objek ammount
    }

    @Override //override method buat transfer 
    public void execute() { //method eksekusi 
        Account account = Account.loadFromFile(accountNumber); //load file
        if (account != null) { //cek file ada/tidak
            account.transfer(amount); //method transfer dari class account sbnyk ammount
        } else {
            System.out.println("Rekening tidak ditemukan.");
        }
    }
}

// Subclass transaksi cek saldo
class CheckBalance extends Transaction { //inherit dari transcantin 
    public CheckBalance(String accountNumber) { //konstruktor buat cek saldo
        super(accountNumber); //panggil konstruktor dari superclass transaction
    }

    @Override //override method buat cek saldo
    public void execute() { //method eksekusi
        Account account = Account.loadFromFile(accountNumber); //load file
        if (account != null) { //cek file ada/tidak
            account.checkBalance(); //panggil method dari class account
        } else {
            System.out.println("Rekening tidak ditemukan.");
        }
    }
}

// Program utama ATM
public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //buat scanner
        Account account = null; //kondisi awal 

        System.out.print("Masukkan nomor rekening: ");
        String accountNumber = scanner.nextLine(); //nerima input pengguna

        // Cek apakah rekening sudah ada
        account = Account.loadFromFile(accountNumber); //load file dari yg sudah di input

        if (account != null) { //cek akun ada gak di file
            // Login dengan PIN
            System.out.print("Masukkan PIN: ");
            String pinInput = scanner.next(); //input pin
            if (!account.getPin().equals(pinInput)) { //cek kesamaan pin
                System.out.println("PIN salah! Program keluar.");
                return;
            }
            System.out.println("Login berhasil! Selamat datang, " + accountNumber); //jika pin bener, maka login
        } else {
            // Buat rekening baru //jika rekning blm ada
            System.out.print("Masukkan nama: "); 
            scanner.nextLine(); // Buang newline dari next() , biar bisa input nama, 
            String name = scanner.nextLine(); //input nama

            System.out.print("Masukkan saldo awal: "); 
            double balance = scanner.nextDouble(); //input saldo awal

            System.out.print("Masukkan PIN: "); 
            String pin = scanner.next(); //input pin baru

            account = new Account(accountNumber, name, balance, pin); //account baru, menerima parameter no rek, nama, saldo, pin
            account.saveToFile(); //simpan ke file
            System.out.println("Rekening berhasil dibuat!"); 
        }

        // Menu transaksi
        while (true) { //selama blm exit bakal lanjut trs
            System.out.println("\nMenu:");
            System.out.println("1. Cek Saldo");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Transfer");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt(); //input pilihan
            if (choice == 4) break; // Keluar dari loop jika pilihan = 4

            switch (choice) {
                case 1:
                    new CheckBalance(accountNumber).execute(); //memanggil method ddari class CheckBalance
                    break;
                case 2:
                    System.out.print("Masukkan jumlah setor: "); //
                    double amount = scanner.nextDouble(); //input jumlah setoran (ammount)
                    new Deposit(accountNumber, amount).execute(); //memanggil method dari class Deposit
                    break;
                case 3:
                    System.out.print("Masukkan jumlah transfer: "); 
                    double transferAmount = scanner.nextDouble(); //input jumlh transfer (ammount)
                    new Transfer(accountNumber, transferAmount).execute(); //memanggil method dari class transfer
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close(); //tutup scanner
    }
}

