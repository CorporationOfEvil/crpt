package ua.dp.edu.crypto.service.encrypt;

public interface EncryptionService
{
    byte[] encrypt(byte[] sourceObject, byte[] key);
}
