package ua.dp.edu.crypto.service.encrypt;

import ua.dp.edu.crypto.util.Util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static ua.dp.edu.crypto.service.key.DummyKeyGenerationService.KEY_PART_DELIMITER;

public class DummyEncryptionService implements EncryptionService
{
    @Override
    public byte[] encrypt(byte[] sourceObject, byte[] key)
    {
        String compositeKey = new String(key, StandardCharsets.UTF_8);
        String[] parts = compositeKey.split(KEY_PART_DELIMITER);
        BigInteger e = new BigInteger(parts[0]);
        BigInteger n = new BigInteger(parts[1]);

        return Util.modPowByte(sourceObject, e, n);
    }
}
