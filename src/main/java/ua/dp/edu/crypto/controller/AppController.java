package ua.dp.edu.crypto.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    @FXML
    private void openEncryptWindow(Event event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("encryptWindow.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 550, 380));
        stage.show();
    }

    @FXML
    private void openDecryptWindow(Event event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("decryptWindow.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    @FXML
    public void openTutorial(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("progress.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 222, 228));
        stage.show();
        System.out.println("hasudhjashdjhasdh");
    }

    @FXML
    public void openTutoriall(Event event)
    {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(scene);

        final VBox vb = new VBox();
        vb.setSpacing(5);

        ProgressIndicator progressBar = new ProgressIndicator();


        ProgressTask progressTask = new ProgressTask(20000, progressBar, stage);

        Thread th = new Thread(progressTask);

        progressBar.progressProperty().bind(progressTask.progressProperty());
        th.setDaemon(true);
        th.start();

        HBox hBox = new HBox();

        hBox.getChildren().add(progressBar);

        vb.getChildren().add(hBox);
        scene.setRoot(vb);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    class ProgressTask extends Task<Void>
    {

        private final Stage stage;
        private int size;
        ProgressIndicator progressBar;

        public ProgressTask(int size, ProgressIndicator progressBar, Stage stage)
        {
            this.size = size;
            this.progressBar = progressBar;
            this.stage = stage;
        }

        @Override
        protected Void call() throws Exception
        {

            for (int i = 0; i < size / 100; i++)
            {

                updateProgress(i, size / 100);
                Thread.sleep(50);

                System.out.println("working");
            }

            stage.hide();
            return null;
        }

    }
}
