public class soal2{
    public static void main(String[] args){
        int i=42;
        String s = (i<40)?"life":(i>50)?"universe":"everything";
        System.out.println(s);
    }
}
/**
 * Bagaimana output setelah dijalankan?
 * Pada terminal dihasilkan kalimat "everything"
 * 
 * Tuliskan teknik yang digunakan
 * Teknik yang digunakan adalah Ternary Operator, yaitu menuliskan banyak baris code ke dalam satu baris kode
 * Biasanya Operator ini mengganti banyak if-else statement menjadi satu baris code
 * ? disini menunjukan kondisi yang harus terpenuhi
 * referensi:
 * https://www.w3schools.com/java/java_conditions_shorthand.asp
 * 
 * 
 * 
 * 
 * 
 */