public class soal1{
    public static void main(String[] args){
        int angka1 = 125;
        int angka2 = 6;
        int hasil = (int) (angka1+angka2);

        System.out.println("Hasil penjumlahannya adalah " + hasil);
    }
}

// Berapa output yang keluar? tuliskan alasan dan referensinya
// Output yang keluar pada terminal ada 1, yaitu ("Hasilnya " + hasil) dimana hasil sudah dideklarasikan sebelumnya
// hasil merupakan fungsi penjumlahan dua variabel yaitu angka1 dan angka2 dengan type data byte dan menghasilkan output senilai -125
// Hasil penjumlahan kedua variabel tersebut seharusnya bernilai 131
// Alasan mengapa pada terminal menghasilkan -125 adalah karena nilai hasil overcapped/overflow
// referensi: 
// https://stackoverflow.com/questions/3001836/how-does-java-handle-integer-underflows-and-overflows-and-how-would-you-check-fo
// byte berukuran 1 byte memiliki batas bawah -128 dan batas atas 127. karena 131 melebihi batas atas maka perhitungan akan mundur ke batas bawah
// sehingga 125 ditambah 6 menjadi
// 125
//  +  
//  6 
//  1    1    1    1    1    1  = 6
//  126 127 -128 -127 -126 -125