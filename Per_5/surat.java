import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.Optional;

// Record Class untuk informasi surat
record SuratInfo(String nama, int jumlahAyat, String arti) {}

public class surat {
    // List - untuk menyimpan surat pendek
    private List<String> suratPendek;
    
    // Set - untuk menyimpan surat yang sudah dihafal (unik)
    private Set<String> suratDihafal;
    
    // Map - untuk menyimpan arti dari surat
    private Map<String, String> artiSurat;
    
    // Immutable Collections
    private final List<String> suratWajib;
    
    // ConcurrentHashMap untuk menghitung berapa kali surat dibaca
    private ConcurrentHashMap<String, Integer> jumlahBacaSurat;
    
    // Queue - untuk antrean pembacaan surat saat ini (Al-Fatihah dan surat yang dipilih)
    private Queue<String> urutanBacaSurat;
    
    // Deque - untuk riwayat surat yang dibaca sebelumnya
    private Deque<String> riwayatBacaSurat;
    
    // Informasi detail surat dengan Record Class
    private List<SuratInfo> detailSurat;
    
    public surat() {
        // Inisialisasi List
        suratPendek = new ArrayList<>();
        suratPendek.add("Al-Ikhlas");
        suratPendek.add("Al-Falaq");
        suratPendek.add("An-Nas");
        suratPendek.add("Al-Kafirun");
        suratPendek.add("Al-Kautsar");
        suratPendek.add("Al-Maun");
        
        // Inisialisasi Set
        suratDihafal = new HashSet<>();
        suratDihafal.add("Al-Ikhlas");
        suratDihafal.add("Al-Falaq");
        suratDihafal.add("An-Nas");
        
        // Inisialisasi Map
        artiSurat = new HashMap<>();
        artiSurat.put("Al-Fatihah", "Pembukaan");
        artiSurat.put("Al-Ikhlas", "Ikhlas");
        artiSurat.put("Al-Falaq", "Waktu Subuh");
        artiSurat.put("An-Nas", "Manusia");
        artiSurat.put("Al-Kafirun", "Orang-orang kafir");
        artiSurat.put("Al-Kautsar", "Nikmat yang berlimpah");
        artiSurat.put("Al-Maun", "Barang yang berguna");
        
        // Inisialisasi Immutable Collections
        suratWajib = List.of("Al-Fatihah");
        
        // Inisialisasi ConcurrentHashMap untuk menghitung berapa kali surat dibaca
        jumlahBacaSurat = new ConcurrentHashMap<>();
        jumlahBacaSurat.put("Al-Fatihah", 100); // Al-Fatihah sudah dibaca banyak kali
        jumlahBacaSurat.put("Al-Ikhlas", 50);
        jumlahBacaSurat.put("Al-Falaq", 25);
        jumlahBacaSurat.put("An-Nas", 30);
        jumlahBacaSurat.put("Al-Kafirun", 20);
        jumlahBacaSurat.put("Al-Kautsar", 15);
        jumlahBacaSurat.put("Al-Maun", 10);
        
        // Inisialisasi Queue - awalnya hanya berisi Al-Fatihah
        // Surat yang dipilih akan ditambahkan nanti
        urutanBacaSurat = new LinkedList<>();
        urutanBacaSurat.offer("Al-Fatihah");
        
        // Inisialisasi Deque - untuk riwayat surat yang dibaca sebelumnya
        // Awalnya kosong, akan diisi nanti
        riwayatBacaSurat = new ArrayDeque<>();
        
        // Inisialisasi List Record
        detailSurat = new ArrayList<>();
        detailSurat.add(new SuratInfo("Al-Fatihah", 7, "Pembukaan"));
        detailSurat.add(new SuratInfo("Al-Ikhlas", 4, "Ikhlas"));
        detailSurat.add(new SuratInfo("Al-Falaq", 5, "Waktu Subuh"));
        detailSurat.add(new SuratInfo("An-Nas", 6, "Manusia"));
        detailSurat.add(new SuratInfo("Al-Kafirun", 6, "Orang-orang kafir"));
        detailSurat.add(new SuratInfo("Al-Kautsar", 3, "Nikmat yang berlimpah"));
        detailSurat.add(new SuratInfo("Al-Maun", 7, "Barang yang berguna"));
    }
    
    // Menampilkan surat pendek yang tersedia
    public void suratPendek() {
        System.out.println("Surat pendek yang tersedia untuk dibaca:");
        System.out.println(suratPendek);
    }
    
    // Mendapatkan list surat pendek
    public List<String> getDaftarSuratPendek() {
        return suratPendek;
    }
    
    // Cek apakah surat sudah dihafal
    public boolean isHafal(String namaSurat) {
        return suratDihafal.contains(namaSurat);
    }
    
    // Mendapatkan arti surat menggunakan Map
    public String getArtiSurat(String namaSurat) {
        return artiSurat.get(namaSurat);
    }
    
    // Mencari surat menggunakan Optional
    public Optional<String> cariSurat(String nama) {
        return suratPendek.stream()
                .filter(s -> s.equalsIgnoreCase(nama))
                .findFirst();
    }
    
    // Menetapkan urutan bacaan surat (Al-Fatihah dan surat yang dipilih)
    public void setUrutanBacaSurat(String namaSurat) {
        // Kosongkan queue terlebih dahulu
        urutanBacaSurat.clear();
        
        // Tambahkan Al-Fatihah (wajib) dan surat yang dipilih
        urutanBacaSurat.offer("Al-Fatihah");
        urutanBacaSurat.offer(namaSurat);
        
        // Catat bahwa Al-Fatihah telah dibaca (selalu bertambah karena wajib)
        tambahJumlahBaca("Al-Fatihah");
    }
    
    // Catat surat yang telah dibaca ke riwayat
    public void tambahKeRiwayat(String namaSurat) {
        riwayatBacaSurat.addLast(namaSurat);
        
        // Catat jumlah kali surat dibaca
        tambahJumlahBaca(namaSurat);
    }
    
    // Menambahkan jumlah kali surat dibaca
    public void tambahJumlahBaca(String namaSurat) {
        jumlahBacaSurat.compute(namaSurat, (k, v) -> (v == null) ? 1 : v + 1);
    }
    
    // Mendapatkan informasi detail surat
    public Optional<SuratInfo> getDetailSurat(String namaSurat) {
        return detailSurat.stream()
                .filter(info -> info.nama().equalsIgnoreCase(namaSurat))
                .findFirst();
    }
    
    // Getter untuk berbagai koleksi
    public Set<String> getSuratDihafal() {
        return suratDihafal;
    }
    
    public Queue<String> getUrutanBacaSurat() {
        return urutanBacaSurat;
    }
    
    public Deque<String> getRiwayatBacaSurat() {
        return riwayatBacaSurat;
    }
    
    public Map<String, Integer> getJumlahBacaSurat() {
        return jumlahBacaSurat;
    }
    
    // Mendapatkan surat yang paling sering dibaca
    public String getSuratTerfavorit() {
        return jumlahBacaSurat.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Tidak ada");
    }
}
