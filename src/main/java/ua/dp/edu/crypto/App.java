package ua.dp.edu.crypto;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        VBox rootNode = new VBox();

        ProgressIndicator progressIndicator = new ProgressIndicator();
        Button button = new Button();

        rootNode.getChildren().add(progressIndicator);
        rootNode.getChildren().add(button);

        button.setOnAction((e) -> {
            DownloadTask downloadTask = new DownloadTask();

            progressIndicator.progressProperty().bind(downloadTask.progressProperty());

            Thread th = new Thread(downloadTask);
            th.setDaemon(true);
            th.start();
        });



        Scene scene = new Scene(rootNode, 400, 200);
        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }

    class DownloadTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            for (int i = 0; i < 100; i++) {

                updateMessage("Progress " + i);
                updateProgress(i, 100);

                Thread.sleep(10);
            }

            return null;
        }

    }}