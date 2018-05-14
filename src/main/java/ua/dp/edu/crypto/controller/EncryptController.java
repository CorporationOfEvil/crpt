package ua.dp.edu.crypto.controller;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import ua.dp.edu.crypto.service.encrypt.DummyEncryptionService;
import ua.dp.edu.crypto.service.encrypt.EncryptionService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class EncryptController implements Initializable
{
    private File sourceFile;
    private File key;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void selectSourceFile(Event event)
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        sourceFile = new FileChooser().showOpenDialog(sourceScene.getWindow());
    }

    public void selectKey(Event event)
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        key = new FileChooser().showOpenDialog(sourceScene.getWindow());
    }

    public void encrypt(Event event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        File targetFile = new FileChooser().showSaveDialog(sourceScene.getWindow());
        if (targetFile != null)
        {
            EncryptionService encryptionService = new DummyEncryptionService();
            try (OutputStream outputStream = Files.newOutputStream(Files.createFile(targetFile.toPath())))
            {
                outputStream.write(encryptionService.encrypt(Files.readAllBytes(sourceFile.toPath()), Files.readAllBytes(key.toPath())));
            }
        }
    }
}
