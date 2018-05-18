package ua.dp.edu.crypto.service.decript;

public interface DecryptionService
{
    byte[] decrypt(byte[] sourceObject, byte[] key);
}
