package ua.dp.edu.crypto.service.encrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import static java.lang.Math.abs;
import static ua.dp.edu.crypto.service.key.DummyKeyGenerationService.KEY_PART_DELIMITER;

public class DummyEncryptionService implements EncryptionService
{
    @Override
    public byte[] encrypt(byte[] sourceObject, byte[] key) {
        Instant start, stop;
        start = Instant.now();
        String compositeKey = new String(key, StandardCharsets.UTF_8);
        String[] parts = compositeKey.split(KEY_PART_DELIMITER);
        BigInteger e = new BigInteger(parts[0]);
        BigInteger n = new BigInteger(parts[1]);

        List<Byte> result = new ArrayList<>();

        for (int i = 0; i < sourceObject.length; i++) {
            int source = sourceObject[i];
            String s = String.valueOf(abs(source));
            byte[] bytes = new BigInteger(s).modPow(e, n).toByteArray();
            List<Byte> collector = new ArrayList<>();
            byte length = (byte) bytes.length;
            collector.add(source >= 0 ? length : (byte) (0 - length));
            collector.addAll(Arrays.asList(ArrayUtils.toObject(bytes)));
            result.addAll(collector);
        }
        stop = Instant.now();
        long al = Duration.between(start, stop).toNanos();
        System.out.println("encryption: " + al);

        return ArrayUtils.toPrimitive(result.toArray(new Byte[result.size()]));

    }
}
