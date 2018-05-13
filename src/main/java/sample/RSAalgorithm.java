package sample;


import java.util.*;
import java.math.BigInteger;

public class RSAalgorithm {


    public static void main(String[] args){
//   ------------------------FIRST ENOUGH LONG VARIANT------------------------
//        //-------------for testing----------------
////        System.out.println("enter p");
////        long p = Long.valueOf(scanner());
//        //-----------------------------------------------
//        long n = 0;
//        long e = 0;
//        long d = 0;
//        boolean prime = false;
//        while (!prime) {
//            SecureRandom secureRan = new SecureRandom();
//            long p = secureRan.nextInt(1234568384);
//
//            MillerRabin mr = new MillerRabin();
//            int k = 5;
//            prime = mr.isPrime(p, k);
//
//            if (prime) {
//// ---------------------for testing---------------
////                System.out.println("enter q");
////                q = Long.valueOf(scanner());
////--------------------------------------------------
//                long q = secureRan.nextInt(1234568384);
//
//                MillerRabin mra = new MillerRabin();
//                k = 5;
//                prime = mra.isPrime(q, k);
//                if (prime) {
//                    n = p * q;
//                    System.out.println("your n = " + n);
//                    long fnElier = (p - 1) * (q - 1);
//                    //for testing
//                    System.out.println("your Elier function " + fnElier);
//                    SecureRandom secureRandom = new SecureRandom();
//                    long gcdNum = 0;
//                    long genE = 0;
//                    while (gcdNum != 1) {
//                        genE = secureRandom.nextInt(1234568384);
//                        while (!(genE < fnElier)) {
//                            genE = secureRandom.nextInt((int) fnElier);
//
//                        }
//                        gcdNum = gcd(genE, fnElier);
//                    }
//                    //for testing
//                    System.out.println("your random number" + genE);
//                    System.out.println("your gcd number " + gcdNum);
//
//                    e = genE;
//
//
//                    long genD = 0;
//                    while (!((e * genD) % fnElier == 1)) {
//                        genD = secureRandom.nextInt(1234568384);
//                        while (!(genD < fnElier)) {
//                            genD = secureRandom.nextInt((int) fnElier);
////                genD = random.nextInt((int) fnElier);
//                        }
//                    }
//                    d = genD; //for better understanding
//                }//            --------------for testing-------------
//                else{
//                    System.out.println("Your number is not prime. Still searching...");
//                }
////            ------------------------------------------
//            }
//
////            --------------for testing-------------
//            else{
//                System.out.println("Your number is not prime. Still searching...");
//            }
////            ------------------------------------------
//        }
//   -------------------------------------------------------------------------------------------------------------------
//
        GenerateKeyPare generateKeyPare = new GenerateKeyPare().invoke();
        BigInteger e = generateKeyPare.getE();
        BigInteger n = generateKeyPare.getN();
        BigInteger d = generateKeyPare.getD();
//   ----------------for testing--------------------------------------

//        System.out.println("d: "+d);
//        System.out.println("e: "+e);
//        System.out.println("n: "+n);
//   ----------------------------------------------------------------------------

        System.out.println("Enter number you want to encrypt");
        BigInteger m = new BigInteger(String.valueOf(Long.parseLong(scanner())));
        // BigInteger c = m.modPow(BigInteger.valueOf(e), BigInteger.valueOf(n));
        BigInteger c = m.modPow(e,n);
        System.out.println(c);
        System.out.println("Do you want to decrypt that number?");
        if (scanner().equals("yes")) {
//            BigInteger mt = c.modPow(BigInteger.valueOf(d), BigInteger.valueOf(n));
            BigInteger mt = c.modPow(d,n);
            System.out.println(mt);
        }


    }

    private static String scanner() {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        return line;
    }

    private static class GenerateKeyPare {
        private BigInteger n;
        private BigInteger e;
        private BigInteger d;

        private static BigInteger generatePublicExponent(int pqBitLength, BigInteger fi) {
            while (true) {
                Random random = new Random();
                int length = pqBitLength + random.nextInt(fi.bitLength() - pqBitLength);
                BigInteger exponent = new BigInteger(length, new Random());
                if (exponent.compareTo(BigInteger.ONE) != 0
                        && exponent.compareTo(fi) == -1
                        && exponent.gcd(fi).compareTo(BigInteger.ONE) == 0) return exponent;
            }
        }

        public BigInteger getN() {
            return n;
        }

        public BigInteger getE() {
            return e;
        }

        public BigInteger getD() {
            return d;
        }

        public GenerateKeyPare invoke() {
            int bitLength =64;
            int pqBitLength;
            BigInteger p;
            BigInteger q;
            n = null;
            BigInteger fi;
            e = null;
            d = null;
            // bitLength = keyBitLength;
            pqBitLength = bitLength / 2;
            boolean primeP = false;
            boolean primeQ = false;
            while (!(primeP && primeQ)) {
                MillerRabin mr = new MillerRabin();
                do {
                    p = BigInteger.probablePrime(pqBitLength, new Random());
                    int k = 5;
                    primeP = mr.isPrime(p.longValue(), k);
                    q = BigInteger.probablePrime(pqBitLength, new Random());
                    primeQ = mr.isPrime(q.longValue(), k);
                    n = p.multiply(q);
                }while (n.bitLength() != bitLength) ;
                fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                e = generatePublicExponent(pqBitLength, fi);
                d = e.modInverse(fi);
            }
            return this;
        }
    }
}
