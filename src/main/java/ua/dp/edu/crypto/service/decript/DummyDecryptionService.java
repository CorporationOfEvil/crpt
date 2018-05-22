package ua.dp.edu.crypto.service.decript;

import org.apache.commons.lang3.ArrayUtils;
import ua.dp.edu.crypto.util.Util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ua.dp.edu.crypto.service.key.DummyKeyGenerationService.KEY_PART_DELIMITER;

public class DummyDecryptionService implements DecryptionService
{
    @Override
    public byte[] decrypt(byte[] sourceObject, byte[] key)
    {
        String compositeKey = new String(key, StandardCharsets.UTF_8);
        String[] parts = compositeKey.split(KEY_PART_DELIMITER);
        BigInteger d = new BigInteger(parts[0]);
        BigInteger n = new BigInteger(parts[1]);

        boolean isLongChunk = false;
        List<Byte> tmp = new ArrayList<>();
        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < sourceObject.length; i++) {
            if (tmp.isEmpty()) {
                isLongChunk = sourceObject[i] == 0;
            }
            tmp.add(sourceObject[i]);
            if ((tmp.size() == 8 && !isLongChunk) || (isLongChunk && tmp.size() == 9)) {
                BigInteger bigInteger = new BigInteger(ArrayUtils.toPrimitive(tmp.toArray(new Byte[tmp.size()])));
                String s = bigInteger.modPow(d,n).toString();
                result.add(Byte.parseByte(s));
                tmp.clear();
                isLongChunk = false;
            }
        }

        return ArrayUtils.toPrimitive(result.toArray(new Byte[result.size()]));
    }
}
