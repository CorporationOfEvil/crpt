package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        URL document = getClass().getClassLoader().getResource("sample.fxml");
        Parent root = FXMLLoader.load(document);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
