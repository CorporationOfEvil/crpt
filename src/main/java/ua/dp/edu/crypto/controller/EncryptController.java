package ua.dp.edu.crypto.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import ua.dp.edu.crypto.service.encrypt.DummyEncryptionService;
import ua.dp.edu.crypto.service.encrypt.EncryptionService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptController implements Initializable
{
    public static final String ENCRYPTED_FILE_EXTENSION = ".rsa";
    private File sourceFile;
    private File key;

    @FXML
    private TextField sourceFilePath;
    @FXML
    private TextField keyPath;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void selectSourceFile(Event event)
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор исходного файла");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        sourceFile = fileChooser.showOpenDialog(sourceScene.getWindow());

        if (sourceFile != null)
        {
            sourceFilePath.setText(sourceFile.getPath());
        }
    }

    public void selectKey(Event event)
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор публичного ключа");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(PUBLIC_KEY_EXTENSION.toUpperCase(), PUBLIC_KEY_EXTENSION));
        key = fileChooser.showOpenDialog(sourceScene.getWindow());

        if (key != null)
        {
            keyPath.setText(key.getPath());
        }
    }

    public void encrypt(Event event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Введите имя для зашифрованного файла");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File targetFile = fileChooser.showSaveDialog(sourceScene.getWindow());
        if (targetFile != null)
        {
            EncryptionService encryptionService = new DummyEncryptionService();

            int index = FilenameUtils.indexOfExtension(sourceFile.getPath());
            String sourceExtension = sourceFile.getPath().substring(index);
            Path path = Paths.get(targetFile.getPath() + sourceExtension + ENCRYPTED_FILE_EXTENSION);

            try (OutputStream outputStream = Files.newOutputStream(Files.createFile(path)))
            {
                outputStream.write(encryptionService.encrypt(Files.readAllBytes(sourceFile.toPath()), Files.readAllBytes(key.toPath())));
                createSuccessfulGenerationMessage();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
            catch (Exception e)
            {
                createWrongFileFormatMessage();
            }

        }
    }

    private void createSuccessfulGenerationMessage()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("");

        alert.showAndWait();
    }

    public void openTutorial(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помощь");
        alert.setHeaderText("Инструкция к шифрованию");

        TextArea textArea = new TextArea("Для того чтобы зашифровать файл:" + "\n" + "1. Нажмите на кнопку 'Выбрать' и укажите файл который вы хотите шифровать."
                + "\n" + "2. Нажмите на кнопку 'Выбрать' и укажите файл открытого ключа. Учтите файл открытого ключа может быть только .pbk формата!"
                + "\n" + "3. Нажмите на кнопку 'Зашифровать' и укажите место для сохранения и имя зашифрованого файла.");
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    private void createWrongFileFormatMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Пожалуйста выбирите файл открытого ключа верного формата." + "\n" + "Файл ключа должен быть формата .prk");

        alert.showAndWait();
    }
}
