import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ua.dp.edu.crypto.service.decript.DecryptionService;
import ua.dp.edu.crypto.service.decript.DummyDecryptionService;
import ua.dp.edu.crypto.service.encrypt.DummyEncryptionService;
import ua.dp.edu.crypto.service.encrypt.EncryptionService;
import ua.dp.edu.crypto.service.key.DummyKeyGenerationService;
import ua.dp.edu.crypto.service.key.KeyGenerationService;

public class Test {

    Path publicPath = Paths.get("/home/dmazan/mly/crpt/src/test/resources/pblk.pbk");
    Path privatePath = Paths.get("/home/dmazan/mly/crpt/src/test/resources/prvk.prk");
    Path sourcePath = Paths.get("/home/dmazan/mly/crpt/src/test/resources/src.txt");

//    @org.junit.Test
    public void testAll() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        EncryptionService encryptionService = new DummyEncryptionService();
        byte[] sourceObject = Files.readAllBytes(sourcePath);
        System.out.println(Arrays.toString(sourceObject));
        System.out.println(new String(sourceObject));

        byte[] encrypt = encryptionService.encrypt(sourceObject, Files.readAllBytes(publicPath));
        System.out.println(Arrays.toString(encrypt));

        DecryptionService decryptionService = new DummyDecryptionService();

        byte[] decrypt = decryptionService.decrypt(encrypt, Files.readAllBytes(privatePath));
        System.out.println(Arrays.toString(decrypt));
    }

    private void generateKeys() throws IOException, NoSuchAlgorithmException {
//        Path publicPath = Paths.get("/home/dmazan/mly/crpt/src/test/resources/pblk.pbk");
//        Path privatePath = Paths.get("/home/dmazan/mly/crpt/src/test/resources/prvk.prk");
        KeyGenerationService keyGenerationService = new DummyKeyGenerationService();
        keyGenerationService.generateKeys(publicPath, privatePath);
    }

    @org.junit.Test
    public void dt() {
        byte[] ar = new byte[]{
                9, 0, -117, 18, 118, 53, 84, 83, 18, -99, //84
                8, 77, -84, -78, -71, -40, -45, 111, 63, //104
                8, 55, 66, -100, 81, 125, -43, -72, 67, //101
                1, 0,
                9, 0, -105, -14, 73, 47, 59, -93, 118, 18, //32
                9, 0, -117, 18, 118, 53, 84, 83, 18, -99, //84
                8, 55, 66, -100, 81, 125, -43, -72, 67, //101
                8, 110, -29, 118, 10, 38, -55, 47, 48, //115
                8, 110, 94, 47, -1, 79, -23, -99, -89, //116
                9, 0, -105, -14, 73, 47, 59, -93, 118, 18 //32
        };

//        10313831236421573369,11577389220627162371
        boolean isStartedCollecting = false;
        byte chunkSize = 0;
        List<Byte> collector = new ArrayList<>();
        for (int i = 0; i < ar.length; i++) {

            if (collector.isEmpty() && !isStartedCollecting) {
                isStartedCollecting = true;
                chunkSize = ar[i];
                continue;
            }
            collector.add(ar[i]);
            chunkSize--;

            if(chunkSize == 0){
                System.out.println(collector);
                collector.clear();
                isStartedCollecting = false;
            }
        }
    }
}
