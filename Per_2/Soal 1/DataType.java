import java.util.Scanner;

public class DataType {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your number");
        

        int i = 0;
        while (true){
            try {
                long number = scanner.nextLong();
                System.out.println(number + " can be fitted in");
                if 
                    (number <= Byte.MAX_VALUE && number >= Byte.MIN_VALUE){
                        System.out.println("> Byte");
                    }
                if
                    (number <= Short.MAX_VALUE && number >= Short.MIN_VALUE){
                        System.out.println("> Short");
                    }
                if
                    (number <= Integer.MAX_VALUE && number >= Integer.MIN_VALUE){
                        System.out.println("> Integer");
                    }
                if
                    (number <= Long.MAX_VALUE && number >= Long.MIN_VALUE){
                        System.out.println("> Byte");
                    }
            } catch(Exception e){
                System.out.println(scanner.next() + "Can't be fitted anywhere");
            } 
            i += 1;           
        }
    }
}
