/*
Nama: Julian Dio Saputra
NIM: 241524049
Matkul Teknik Pemrograman
*/
/*
1. list, set map
2. record class
3. optional
4. concurrent collections
5. queue dan dequeue
6. immuatble collection list.of, set.of, map.of
*/

import java.util.Scanner;
import java.util.Optional;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kamu saat ini sedang ibadah solat, setelah membaca Al-Fatihah, kamu ingin membaca surat pendek.");
        
        // Membuat objek surat
        surat myObj = new surat();
        
        // Menampilkan daftar surat pendek
        myObj.suratPendek();
        
        System.out.print("Pilih surat yang ingin dibaca: ");
        String pilihanSurat = scanner.nextLine();
        
        // Menggunakan Optional untuk mencari surat
        Optional<String> hasilPencarian = myObj.cariSurat(pilihanSurat);
        
        if (hasilPencarian.isPresent()) {
            String surat = hasilPencarian.get();
            System.out.println("Kamu memilih surat " + surat);
            
            // Cek apakah surat sudah dihafal
            if (myObj.isHafal(surat)) {
                System.out.println("Surat " + surat + " sudah kamu hafal.");
                System.out.println("Arti surat: " + myObj.getArtiSurat(surat));
                
                // Dapatkan detail surat menggunakan Optional dan Record Class
                Optional<SuratInfo> detailOpt = myObj.getDetailSurat(surat);
                if (detailOpt.isPresent()) {
                    SuratInfo detail = detailOpt.get();
                    System.out.println("Detail surat:");
                    System.out.println("Nama: " + detail.nama());
                    System.out.println("Jumlah ayat: " + detail.jumlahAyat());
                    System.out.println("Arti: " + detail.arti());
                }
                
                // Set urutan bacaan surat: Al-Fatihah dan surat yang dipilih
                myObj.setUrutanBacaSurat(surat);
                System.out.println("\nUrutan surat yang akan dibaca: " + myObj.getUrutanBacaSurat());
                
                // Simulasi pembacaan surat
                System.out.println("\nMembaca surat...");
                
                // Setelah dibaca, tambahkan ke riwayat bacaan
                myObj.tambahKeRiwayat(surat);
                System.out.println("Surat " + surat + " telah dibaca dan ditambahkan ke riwayat.");
                
                // Tampilkan riwayat surat yang telah dibaca (menggunakan Deque)
                System.out.println("\nRiwayat surat yang telah dibaca sebelumnya: " + myObj.getRiwayatBacaSurat());
                
                // Tampilkan jumlah berapa kali surat dibaca
                System.out.println("\nJumlah kali surat dibaca:");
                myObj.getJumlahBacaSurat().forEach((k, v) -> 
                    System.out.println(k + ": " + v + " kali")
                );
                
                // Tampilkan surat terfavorit (yang paling sering dibaca)
                System.out.println("\nSurat yang paling sering dibaca: " + myObj.getSuratTerfavorit());
            } else {
                System.out.println("Surat " + surat + " belum kamu hafal. Pilihlah surat lain.");
                
                // Tampilkan surat yang sudah dihafal (menggunakan Set)
                System.out.println("Surat yang sudah kamu hafal: " + myObj.getSuratDihafal());
            }
        } else {
            System.out.println("Surat " + pilihanSurat + " tidak ditemukan dalam daftar.");
            
            // Tampilkan surat yang tersedia
            System.out.println("Surat yang tersedia: " + myObj.getDaftarSuratPendek());
        }
        
        scanner.close();
    }
}

/*
Algoritma cariSurat(nama):
    Input: nama (String) - nama surat yang dicari
    Output: Optional<String> - hasil pencarian, mungkin kosong

    1. Ambil koleksi suratPendek
    2. Konversi ke stream dengan stream()
    3. Filter elemen dengan filter(predicate):
       3.1. Untuk setiap elemen s, periksa s.equalsIgnoreCase(nama)
       3.2. Hanya elemen yang memenuhi kondisi yang diteruskan
    4. Ambil elemen pertama yang cocok dengan findFirst()
       4.1. Jika ditemukan, hasilnya adalah Optional berisi nilai tersebut
       4.2. Jika tidak, hasilnya adalah Optional.empty()
    5. Kembalikan hasil Optional

Algoritma tambahJumlahBaca(namaSurat):
    Input: namaSurat (String) - nama surat yang akan ditambah hitungannya
    Output: void - memodifikasi state internal jumlahBacaSurat

    1. Akses ConcurrentHashMap jumlahBacaSurat
    2. Panggil compute(key, remappingFunction):
       2.1. Key: namaSurat
       2.2. RemappingFunction (BiFunction): (k, v) -> (v == null) ? 1 : v + 1
            - Jika nilai saat ini (v) adalah null, kembalikan 1
            - Jika nilai ada, tambahkan 1 ke nilai saat ini
    3. Operasi compute() dilakukan secara atomik (thread-safe)
       3.1. Hanya satu thread yang dapat memodifikasi key tertentu pada satu waktu
       3.2. Tidak ada race condition saat membaca dan mengupdate
    
Algoritma getSuratTerfavorit():
    Input: none - menggunakan state internal jumlahBacaSurat
    Output: String - nama surat yang paling sering dibaca

    1. Ambil entrySet dari jumlahBacaSurat (Set<Map.Entry<String, Integer>>)
    2. Konversi ke stream dengan stream()
    3. Gunakan max(comparator) untuk menemukan entry dengan nilai tertinggi:
       3.1. Comparator: Map.Entry.comparingByValue()
       3.2. Membandingkan entry berdasarkan value (jumlah baca)
    4. Hasil max() adalah Optional<Map.Entry<String, Integer>>
    5. Gunakan map(function) untuk mengekstrak key dari entry:
       5.1. Function: Map.Entry::getKey
       5.2. Konversi dari Optional<Map.Entry> ke Optional<String>
    6. Gunakan orElse("Tidak ada") untuk menangani kasus ketika map kosong
    7. Kembalikan hasil String

Algoritma tambahKeRiwayat(namaSurat):
    Input: namaSurat (String) - nama surat yang telah dibaca
    Output: void - memodifikasi state internal riwayatBacaSurat dan jumlahBacaSurat

    1. Akses Deque riwayatBacaSurat
    2. Tambahkan namaSurat ke akhir deque dengan addLast(namaSurat)
       2.1. Elemen ditambahkan ke ujung deque
       2.2. Deque bertambah ukurannya
    3. Panggil tambahJumlahBaca(namaSurat) untuk memperbaharui counter
       3.1. Incremet jumlah kali surat dibaca (tidak berhasil)

CopyAlgoritma setUrutanBacaSurat(namaSurat):
    Input: namaSurat (String) - nama surat yang akan dibaca
    Output: void - memodifikasi state internal urutanBacaSurat dan jumlahBacaSurat

    1. Akses Queue urutanBacaSurat
    2. Kosongkan queue dengan clear()
       2.1. Menghapus semua elemen dari queue
    3. Tambahkan "Al-Fatihah" (wajib) dengan offer("Al-Fatihah")
       3.1. Elemen pertama dalam queue
    4. Tambahkan namaSurat dengan offer(namaSurat)
       4.1. Elemen kedua dalam queue
    5. Catat bahwa Al-Fatihah telah dibaca dengan tambahJumlahBaca("Al-Fatihah")
       5.1. Increment counter untuk Al-Fatihah

Algoritma getDetailSurat(namaSurat):
    Input: namaSurat (String) - nama surat yang dicari detailnya
    Output: Optional<SuratInfo> - detail surat, mungkin kosong

    1. Ambil koleksi detailSurat (List<SuratInfo>)
    2. Konversi ke stream dengan stream()
    3. Filter record berdasarkan nama:
       3.1. filter(info -> info.nama().equalsIgnoreCase(namaSurat))
       3.2. Memanggil accessor method nama() dari Record
    4. Ambil record pertama yang cocok dengan findFirst()
       4.1. Jika ditemukan, hasilnya adalah Optional berisi SuratInfo
       4.2. Jika tidak, hasilnya adalah Optional.empty()
    5. Kembalikan hasil Optional

Kamu saat ini sedang ibadah solat, setelah membaca Al-Fatihah, kamu ingin membaca surat pendek.
Surat pendek yang tersedia untuk dibaca:
[Al-Ikhlas, Al-Falaq, An-Nas, Al-Kafirun, Al-Kautsar, Al-Maun]
Pilih surat yang ingin dibaca: Al-Falaq
Kamu memilih surat Al-Falaq
Surat Al-Falaq sudah kamu hafal.
Arti surat: Waktu Subuh
Detail surat:
Nama: Al-Falaq
Jumlah ayat: 5
Arti: Waktu Subuh
Updated count for Al-Fatihah: 101
Updated count for Al-Falaq: 26

Urutan surat yang akan dibaca: [Al-Fatihah, Al-Falaq]  

Membaca surat...
Updated count for Al-Falaq: 27
Surat Al-Falaq telah dibaca dan ditambahkan ke riwayat.

Riwayat surat yang telah dibaca sebelumnya: [Al-Falaq] 

Jumlah kali surat dibaca:
Al-Falaq: 27 kali   
Al-Maun: 10 kali    
Al-Kafirun: 20 kali 
Al-Ikhlas: 50 kali  
Al-Kautsar: 15 kali 
Al-Fatihah: 101 kali
An-Nas: 30 kali     

Surat yang paling sering dibaca: Al-Fatihah */
