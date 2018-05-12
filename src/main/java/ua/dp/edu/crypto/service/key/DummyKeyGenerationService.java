package ua.dp.edu.crypto.service.key;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DummyKeyGenerationService implements KeyGenerationService
{
    private static final String PUBLIC_KEY_NAME = "public.txt";
    private static final String PRIVATE_KEY_NAME = "private.txt";

    @Override
    public void generatePublicKey(Path destinationPath) throws IOException
    {
        String publicKey = "simple_test_public_key";
        storeKeyToFile(destinationPath, PUBLIC_KEY_NAME, publicKey.getBytes());
    }

    @Override
    public void generatePrivateKey(Path destinationPath) throws IOException
    {
        String publicKey = "simple_test_private_key";
        storeKeyToFile(destinationPath, PRIVATE_KEY_NAME, publicKey.getBytes());
    }

    private void storeKeyToFile(Path destinationPath, String fileName, byte[] data) throws IOException
    {
        Path path = Paths.get(destinationPath + File.separator + fileName);
        if (Files.exists(path))
        {
            throw new IllegalArgumentException(String.format("There is already created key file %s, in specified path %s", fileName, path.toString()));
        }
        try (OutputStream outputStream = Files.newOutputStream(Files.createFile(path)))
        {
            outputStream.write(data);
        }

    }
}
