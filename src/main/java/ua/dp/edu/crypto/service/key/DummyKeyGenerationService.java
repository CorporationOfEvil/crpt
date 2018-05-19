package ua.dp.edu.crypto.service.key;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static ua.dp.edu.crypto.controller.KeyGenerationController.PRIVATE_KEY_EXTENSION;
import static ua.dp.edu.crypto.controller.KeyGenerationController.PUBLIC_KEY_EXTENSION;

public class DummyKeyGenerationService implements KeyGenerationService
{
    public static final String KEY_PART_DELIMITER = ",";
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    @Override
    public void generateKeys(Path publicKeyPath, Path privateKeyPath) throws IOException
    {
        generateKes();

        storeKeyToFile(publicKeyPath, String.join(KEY_PART_DELIMITER, e.toString(), n.toString()).getBytes(), PUBLIC_KEY_EXTENSION);

        storeKeyToFile(privateKeyPath, String.join(KEY_PART_DELIMITER, d.toString(), n.toString()).getBytes(), PRIVATE_KEY_EXTENSION);
    }

    private void generateKes()
    {
        int bitLength = 64;
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
        while (!(primeP && primeQ))
        {
            MillerRabin mr = new MillerRabin();
            do
            {
                p = BigInteger.probablePrime(pqBitLength, new Random());
                int k = 5;
                primeP = mr.isPrime(p.longValue(), k);
                q = BigInteger.probablePrime(pqBitLength, new Random());
                primeQ = mr.isPrime(q.longValue(), k);
                n = p.multiply(q);
            } while (n.bitLength() != bitLength);
            fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            e = generatePublicExponent(pqBitLength, fi);
            d = e.modInverse(fi);
        }

    }

    private BigInteger generatePublicExponent(int pqBitLength, BigInteger fi)
    {
        while (true)
        {
            Random random = new Random();
            int length = pqBitLength + random.nextInt(fi.bitLength() - pqBitLength);
            BigInteger exponent = new BigInteger(length, new Random());
            if (exponent.compareTo(BigInteger.ONE) != 0 && exponent.compareTo(fi) == -1 && exponent.gcd(fi).compareTo(BigInteger.ONE) == 0)
            {
                return exponent;
            }
        }
    }

    private void storeKeyToFile(Path destinationPath, byte[] data, String fileExtension) throws IOException
    {
        Path path = Paths.get(destinationPath + fileExtension);

        if (Files.exists(path))
        {
            throw new IllegalArgumentException(String.format("There is already created key file %s", path.toString()));
        }
        try (OutputStream outputStream = Files.newOutputStream(Files.createFile(path)))
        {
            outputStream.write(data);
        }
    }
}
