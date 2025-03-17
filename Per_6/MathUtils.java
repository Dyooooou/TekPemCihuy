public class MathUtils{
    //-------------------------------------------------------------
    // Returns the factorial of the argument given
    //-------------------------------------------------------------
    public static int factorial(int n) throws IllegalArgumentException {
        if (n < 0)
            throw new IllegalArgumentException("Faktorial tidak didefinisikan untuk angka negatif");
        if (n > 16)
            throw new IllegalArgumentException("Faktorial untuk angka lebih dari 16 melebihi batas tipe int");
            
        int fac = 1;
        for (int i=n; i>0; i--)
            fac *= i;
        return fac;
    }
}