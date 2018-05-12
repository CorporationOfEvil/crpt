package ua.dp.edu.crypto.service.key;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Basic interface for all services which have to generate pair of keys and store them to files.
 */
public interface KeyGenerationService
{
    /**
     * Generates and stores public key to specified path.
     */
    void generatePublicKey(Path destinationPath) throws IOException;

    /**
     * Generates and stores private key to specified path.
     */
    void generatePrivateKey(Path destinationPath) throws IOException;

}
