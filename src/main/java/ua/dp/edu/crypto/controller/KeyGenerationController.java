package ua.dp.edu.crypto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyGenerationController implements Initializable
{
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    private void selectKeyDirectiry(ActionEvent event) throws IOException
    {
        Scene sourceScene = ((Node) (event.getSource())).getScene();
        File selectedFolder = new DirectoryChooser().showDialog(sourceScene.getWindow());
        generateKeys(selectedFolder);

    }

    private void generateKeys(File destinationFolder) throws IOException
    {
        if (destinationFolder != null && destinationFolder.isDirectory())
        {
            System.out.println("You've selected " + destinationFolder.getCanonicalPath());
        }
    }
}
