package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class CommentFormController {

    @FXML
    private TextField textField;

    @FXML
    private Label commentLabel;

    @FXML
    void initialize() {
        commentLabel.setText("Введите комментарий:");

        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
            {
                User userForRequest = makeUserInfo(User.getCurrentUserLogin(),
                        String.valueOf(Car.getCurrentCarId()) , textField.getText(), false, false);
                Connection.writeObject(userForRequest, Commands.AddComment);

                textField.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/comments.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            }

        });
    }

    private User makeUserInfo(String login, String password, String email, boolean isAdmin, boolean isLocked) {
        User temp = new User();
        temp.setLogin(login);
        temp.setPassword(password);
        temp.setEmail(email);
        temp.setAdmin(isAdmin);
        temp.setLocked(isLocked);

        return temp;
    }
}
