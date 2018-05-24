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
import ua.dp.edu.crypto.service.decript.DecryptionService;
import ua.dp.edu.crypto.service.decript.DummyDecryptionService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static ua.dp.edu.crypto.controller.EncryptController.ENCRYPTED_FILE_EXTENSION;

public class DecryptController implements Initializable
{
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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        key = fileChooser.showOpenDialog(sourceScene.getWindow());
        if (key != null)
        {
            keyPath.setText(key.getPath());
        }
    }

    public void decrypt(Event event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File targetFile = fileChooser.showSaveDialog(sourceScene.getWindow());
        if (targetFile != null)
        {
            DecryptionService decryptionService = new DummyDecryptionService();

            Path path = Paths.get(targetFile.getPath());

            int index = FilenameUtils.indexOfExtension(sourceFile.getPath());
            String encryptedExt = sourceFile.getPath().substring(index);
            if (ENCRYPTED_FILE_EXTENSION.equals(encryptedExt))
            {
                String s = sourceFile.getPath().substring(0, index);
                int originalIndex = FilenameUtils.indexOfExtension(s);
                if (originalIndex > 0)
                {
                    int originalExtIndex = FilenameUtils.indexOfExtension(s);

                    path = Paths.get(targetFile.getPath() + s.substring(originalExtIndex));
                }

            }

            try (OutputStream outputStream = Files.newOutputStream(Files.createFile(path)))
            {
                outputStream.write(decryptionService.decrypt(Files.readAllBytes(sourceFile.toPath()), Files.readAllBytes(key.toPath())));
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
        alert.setHeaderText("Файл был успешно расшифрован и сохранен");

        alert.showAndWait();
    }

    public void openTutorial(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помощь");
        alert.setHeaderText("Инструкция к дешифрованию");

        TextArea textArea = new TextArea("Для того чтобы дешифровать файл:" + "\n" + "1. Нажмите на кнопку 'Выбрать' и укажите файл который вы хотите дешифровать. Учтите, файл для дешифрования может быть только *** формата!"
                + "\n" + "2. Нажмите на кнопку 'Выбрать' и укажите файл личного ключа. Учтите файл личного ключа может быть только .prk формата!"
                + "\n" + "3. Нажмите на кнопку 'Расшифровать' и укажите место для сохранения и имя расшифрованого файла.");
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
        alert.setHeaderText("Пожалуйста выбирите файл верного формата." + "\n" +
                        "Файл ключа должен быть формата .prk,а файл для расшифровки может быть только формата .чтотот");

        alert.showAndWait();
    }
}
