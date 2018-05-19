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
import ua.dp.edu.crypto.service.decript.DecryptionService;
import ua.dp.edu.crypto.service.decript.DummyDecryptionService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

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
            try (OutputStream outputStream = Files.newOutputStream(Files.createFile(targetFile.toPath())))
            {
                outputStream.write(decryptionService.decrypt(Files.readAllBytes(sourceFile.toPath()), Files.readAllBytes(key.toPath())));
            }
            createSuccessfulGenerationMessage();

            ((Node) event.getSource()).getScene().getWindow().hide();

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
        alert.setHeaderText("Инструкция к данному ПО");

        TextArea textArea = new TextArea("А тут Лена напишет инструкцию к применению данного приложния, с описанием использованных алгоритмов  и подходов");
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
}
