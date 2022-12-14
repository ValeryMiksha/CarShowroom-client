package controllers;

import commands.Commands;
import connection.Connection;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label authorize;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;
    @FXML
    private Button signUpButton;

    private final int USER_IN_DATABASE=0;

    private final int CHECK_APPROVED_NOT_ADMIN=1;
    private final int CHECK_APPROVED_IS_ADMIN=2;
    private final int CHECK_NOT_APPROVED=3;
    private final int NO_SUCH_USER=8;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            String userLogin = loginField.getText().trim();
            String userPassword = passwordField.getText().trim();

            if (!userLogin.equals("") && !userPassword.equals(""))
            {
                switch(authorizeUser(userLogin,userPassword))
                {
                    case CHECK_APPROVED_IS_ADMIN:
                    {
                        registrationButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/admin.fxml"));

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
                        break;
                    }
                    case CHECK_APPROVED_NOT_ADMIN:
                    {
                        User.setCurrentUserLogin(userLogin);
                        registrationButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/user.fxml"));

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
                        break;
                    }
                    case CHECK_NOT_APPROVED:
                    {
                        authorize.setText("Вы заблокированы!");
                        break;
                    }
                    case NO_SUCH_USER:
                    {
                        authorize.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/home.fxml"));

                        try {
                            loader.load();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                        break;
                    }
                }
            }
            else
                authorize.setText("Логин или пароль неправильный");
        });

        registrationButton.setOnAction(actionEvent -> {
            registrationButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/registration.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        });
    }

    private int authorizeUser(String userLogin, String userPassword) {
        User userForRequest=makeUserInfo(userLogin,userPassword,"",false,false);
        Connection.writeObject(userForRequest, Commands.CheckLoginAndPassword);

        while(true)
        {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                boolean isAdmin = (boolean) parseMap.get("isAdmin");
                boolean isLocked=(boolean) parseMap.get("isLocked");
                if(!isLocked)
                {
                    if (command == USER_IN_DATABASE) {
                        if (isAdmin)
                            return CHECK_APPROVED_IS_ADMIN;
                        return CHECK_APPROVED_NOT_ADMIN;
                    }
                    if (command == NO_SUCH_USER)
                        return NO_SUCH_USER;
                }
                break;
            }
        }
        return CHECK_NOT_APPROVED;
    }
    private User makeUserInfo(String login, String password, String email, boolean isAdmin, boolean isLocked)
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
