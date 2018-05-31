package ua.dp.edu.crypto.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Генерация пары ключей");
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    @FXML
    private void openEncryptWindow(Event event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("encryptWindow.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Шифрование");
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    @FXML
    private void openDecryptWindow(Event event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("decryptWindow.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Дешифрование");
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    public void openTutorial(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помощь");
        alert.setHeaderText("Инструкция к данному ПО");

        TextArea textArea = new TextArea("Данная програма шифрует и дешифрует файлы на основе алгоритма RSA в котором используются два ключа." + "\n"
        + "1.) Для того чтоб сгенерировать пару ключей, нажмите на 'Генерировать ключи'. Далее укажите место для хранения файла и имя ключа;"
                + "\n" +"2.) Для того чтоб зашифровать файл, нажмите на 'Зашифровать'. Далее выбирите выбирите файл для шифрования и открытый ключ, после чего выбирите место хранения и имя для зашифрованного файла;"
                + "\n" + "3.) Для того чтоб расшифровать файл, нажмите на 'Расшифровать'. Далее выбирите выбирите файл для расшифрования и личный ключ, после чего выбирите место хранения и имя для зашифрованного файла;");
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

    public void menuBar(Event event) throws IOException {

//        не получилось добавить отлавливание ивента и открывать на него окно помощи:(

        final MenuBar menuBar = new MenuBar();
        final Menu fileMenu = new Menu("Файл");
        final Menu helpMenu = new Menu("Помощь");
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("decryptWindow.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 550, 350));
        stage.show();
    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }
}
