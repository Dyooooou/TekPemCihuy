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

/*
1. dengan setiap bagian diatas mencakup beberapa pertanyaan
a. Jelaskan Definisi
b. Alasan diperlukan
c. contoh kasus dunia nyata
*/


import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kamu saat ini sedang ibadah solat, setelah membaca Al-Fatihah, kamu ingin membaca surat pendek.");
        System.out.println("Berikut adalah surat pendek yang terpikirkan olehmu");

        surat myObj = new surat();
        myObj.suratPendek();
        // List<String> surat = MyObj.surat();

        String A = scanner.next();

        System.out.println("Kamu pun memilih surat " + A + " untuk dibaca setelah Al-Fatihah");

        // while (true){
        //     // if (A.equals(surat(indexOf()))){
        //     if (surat.contains(A)){
        //         System.out.println("Surat " + A + " kamu hafal.");
        //         break;
        //     }else{
        //         System.out.println("Surat " + A + " tidak kamu hafal. Pilih surat lain");
        //         A = scanner.next();
        //     }

        // }
        scanner.close();
    }
    
}
