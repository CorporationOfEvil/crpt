package ua.dp.edu.crypto.service.key;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/**
 * Basic interface for all services which have to generate pair of keys and store them to files.
 */
public interface KeyGenerationService
{
    /**
     * Generates and stores public and private keys to specified paths.
     */
    void generateKeys(Path publicKeyPath, Path privateKeyPath) throws IOException, NoSuchAlgorithmException;
}
