package ua.dp.edu.crypto.service.encrypt;

import org.apache.commons.lang3.ArrayUtils;

public class DummyEncryptionService implements EncryptionService
{
    @Override
    public byte[] encrypt(byte[] sourceObject, byte[] key)
    {
        String prefix = "Source object was encrypted \n";
        String postfix = "\n with key \n";
        byte[] tmp = ArrayUtils.addAll(prefix.getBytes(), sourceObject);
        return ArrayUtils.addAll(tmp, ArrayUtils.addAll(postfix.getBytes(), key));
    }
}
