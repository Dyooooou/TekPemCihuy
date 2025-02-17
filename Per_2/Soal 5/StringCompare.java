import java.util.Scanner;

public class StringCompare{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String A = scanner.next();
        String B = scanner.next();

        int sumLength = (int) A.length() + B.length();
        System.out.println("sum of the length of String A & String B: " + sumLength);

        
        if(A.compareTo(B) > 0){
            System.out.println("Yes");
        } else{
            System.out.println("No");
        }

        String capitalizeA = A.substring(0, 1).toUpperCase() + A.substring(1);
        String capitalizeB = B.substring(0, 1).toUpperCase() + B.substring(1);
        System.out.println(capitalizeA + " " + capitalizeB);
        scanner.close();
    }
}