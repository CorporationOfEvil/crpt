package ua.dp.edu.crypto.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import ua.dp.edu.crypto.service.key.DummyKeyGenerationService;
import ua.dp.edu.crypto.service.key.KeyGenerationService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyGenerationController implements Initializable
{
    public static final String PUBLIC_KEY_EXTENSION = ".pbk";
    public static final String PRIVATE_KEY_EXTENSION = ".prk";

    private File openKeyFolder;
    private File privateKeyFolder;

    @FXML
    private TextField openKeyPath;
    @FXML
    private TextField privateKeyPath;

    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    private void selectOpenKeyDirectory(MouseEvent event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        openKeyFolder = chooseFileForCreation(sourceScene, "публичного", PUBLIC_KEY_EXTENSION);
        if (openKeyFolder != null)
        {
            openKeyPath.setText(openKeyFolder.getPath() + PUBLIC_KEY_EXTENSION);
        }
    }

    @FXML
    public void selectPrivateKeyDirectory(Event event)
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        privateKeyFolder = chooseFileForCreation(sourceScene, "приватного", PRIVATE_KEY_EXTENSION);
        if (privateKeyFolder != null)
        {
            privateKeyPath.setText(privateKeyFolder.getPath() + PRIVATE_KEY_EXTENSION);
        }
    }

    @FXML
    private void generateKeys(MouseEvent event)
    {
        if (openKeyFolder != null && privateKeyFolder != null)
        {
            KeyGenerationService generationService = new DummyKeyGenerationService();

            try
            {
                generationService.generateKeys(openKeyFolder.toPath(), privateKeyFolder.toPath());

                createSuccessfulGenerationMessage();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
            catch (Exception e)
            {
                createFileCreationErrorMessage(e);
            }
        }
        else
        {
            createNotSelectedDirectoryWarning();
        }
    }

    private File chooseFileForCreation(Scene sourceScene, String keyName, String extension)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(String.format("Генерация %s ключа", keyName));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(extension.toUpperCase(), extension));
        return fileChooser.showSaveDialog(sourceScene.getWindow());
    }

    private void createSuccessfulGenerationMessage()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Keys were successfully generated and stored");

        alert.showAndWait();
    }

    private void createNotSelectedDirectoryWarning()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Please select directory for generation.");
        alert.showAndWait();
    }

    private void createFileCreationErrorMessage(Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("Exception during key generation");

        TextArea textArea = new TextArea(e.getMessage());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
