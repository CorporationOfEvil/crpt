package ua.dp.edu.crypto.service.decript;

import ua.dp.edu.crypto.util.Util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

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

        return Util.modPowByte(sourceObject, d, n);
    }
}
