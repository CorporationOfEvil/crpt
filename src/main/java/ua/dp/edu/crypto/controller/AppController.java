package ua.dp.edu.crypto.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable
{
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    private void openKeyGenerationWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("keyGenerationWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
    }
}
