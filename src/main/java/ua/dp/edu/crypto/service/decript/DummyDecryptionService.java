package ua.dp.edu.crypto.service.decript;

public class DummyDecryptionService implements DecryptionService
{
    @Override
    public byte[] decrypt(byte[] sourceObject, byte[] key)
    {
        return "decrypted version".getBytes();
    }
}
