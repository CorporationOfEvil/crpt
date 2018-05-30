package ua.dp.edu.crypto.service.decript;

import org.apache.commons.lang3.ArrayUtils;
import ua.dp.edu.crypto.util.Util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static ua.dp.edu.crypto.service.key.DummyKeyGenerationService.KEY_PART_DELIMITER;

public class DummyDecryptionService implements DecryptionService {
    @Override
    public byte[] decrypt(byte[] sourceObject, byte[] key) {
        Instant start, stop;
        start = Instant.now();
        String compositeKey = new String(key, StandardCharsets.UTF_8);
        String[] parts = compositeKey.split(KEY_PART_DELIMITER);
        BigInteger d = new BigInteger(parts[0]);
        BigInteger n = new BigInteger(parts[1]);

        boolean isStartedCollecting = false;
        boolean positiveSign = false;
        byte chunkSize = 0;
        List<Byte> tmp = new ArrayList<>();
        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < sourceObject.length; i++) {
            if (tmp.isEmpty() && !isStartedCollecting) {
                chunkSize = (byte) abs(sourceObject[i]);
                isStartedCollecting = true;
                positiveSign = sourceObject[i] >= 0;
                continue;
            }

            tmp.add(sourceObject[i]);
            chunkSize--;

            if (chunkSize == 0) {
                BigInteger bigInteger = new BigInteger(ArrayUtils.toPrimitive(tmp.toArray(new Byte[tmp.size()])));
                String s = bigInteger.modPow(d, n).toString();
                int e = Integer.parseInt(s);
                result.add(positiveSign ? (byte) e : (byte) (0 - e));
                tmp.clear();
                isStartedCollecting = false;
            }
        }
        stop = Instant.now();
        long alr = Duration.between(start, stop).toNanos();
        System.out.println("decryption: " + alr);

        return ArrayUtils.toPrimitive(result.toArray(new Byte[result.size()]));
    }
}
