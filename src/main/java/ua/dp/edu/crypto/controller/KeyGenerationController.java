package ua.dp.edu.crypto.controller;

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
import javafx.stage.DirectoryChooser;
import ua.dp.edu.crypto.service.key.DummyKeyGenerationService;
import ua.dp.edu.crypto.service.key.KeyGenerationService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyGenerationController implements Initializable
{
    private File selectedFolder;

    @FXML
    private TextField selectedPath;

    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    private void selectKeyDirectory(MouseEvent event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        selectedFolder = new DirectoryChooser().showDialog(sourceScene.getWindow());
        if (selectedFolder != null)
        {
            selectedPath.setText(selectedFolder.getPath());
        }
    }

    @FXML
    private void generateKeys(MouseEvent event)
    {
        if (selectedFolder != null && selectedFolder.isDirectory())
        {
            KeyGenerationService generationService = new DummyKeyGenerationService();

            try
            {
                generationService.generatePublicKey(selectedFolder.toPath());
                generationService.generatePrivateKey(selectedFolder.toPath());

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
