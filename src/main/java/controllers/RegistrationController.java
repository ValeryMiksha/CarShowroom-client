package controllers;

import commands.Commands;
import connection.Connection;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;


public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField adminCodeField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    private int currentWishListId=1;



    @FXML
    void initialize() {
        registrationButton.setOnAction(actionEvent -> {

            registerUser();

            adminCodeField.getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();



        });
    }

    private void registerUser() {
        boolean isAdmin, isLocked;
        String login = loginField.getText().trim();
        String password = String.valueOf(Objects.hashCode(passwordField.getText().trim()));
        String email = mailField.getText().trim();
        if (adminCodeField.getText().trim().equals("1234"))
            isAdmin = true;
        else isAdmin = false;
        isLocked = false;
        User userInfo = makeUserInfo(login, password, email, isAdmin, isLocked);
        Connection.writeObject(userInfo, Commands.Register);

    }
    private User makeUserInfo(String login,String password,String email,boolean isAdmin,boolean isLocked)
    {
        User temp=new User();
        temp.setLogin(login);
        temp.setPassword(password);
        temp.setEmail(email);
        temp.setAdmin(isAdmin);
        temp.setLocked(isLocked);

        return temp;
    }

}
