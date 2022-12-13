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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField changePasswordField;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label mailField;

    @FXML
    private Label loginField;

    @FXML
    private Button catalogButton;

    @FXML
    private Button wishListButton;


    private User userInfo;

    private final int USER_INFO_GET=1;

    @FXML
    void initialize() {
        User userForRequest=makeUserInfo(User.getCurrentUserLogin(),"","",false,false);
        Connection.writeObject(userForRequest, Commands.GetUserInfoLogin);

        while(true)
        {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if(command==USER_INFO_GET)
                {
                    userInfo=makeUserInfo(
                            parseMap.get("login").toString(),
                            parseMap.get("password").toString(),
                            parseMap.get("email").toString(),
                            (boolean) parseMap.get("isAdmin"),
                            (boolean) parseMap.get("isLocked")
                    );
                    break;
                }
            }
        }

        loginField.setText(userInfo.getLogin());
        mailField.setText(userInfo.getEmail());




/*
        if(Kursach.IsLightTheme)
            ActorP.setStyle(ThemeAndLanguage.Light);
        else
            ActorP.setStyle(ThemeAndLanguage.Dark);


        if(Kursach.IsRussionLanguage)
        {
            Profile_btn.setText(ThemeAndLanguage.ProfileBtn);
            Product_btn.setText(ThemeAndLanguage.ProductBtn);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtn);
            Chat_btn.setText(ThemeAndLanguage.ChatBtn);
            Statistic_btn.setText(ThemeAndLanguage.StatisticBtn);
            Exit_btn.setText(ThemeAndLanguage.ExitBtn);


            One.setText(ThemeAndLanguage.Firstname);
            Two.setText(ThemeAndLanguage.Lastname);
            Three.setText(ThemeAndLanguage.Gender);
            Four.setText(ThemeAndLanguage.Username);
            Five.setText(ThemeAndLanguage.Balance);


            UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtn);
            ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtn);
            ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalance);
            ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePassword);

        }else {

            Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
            Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
            Chat_btn.setText(ThemeAndLanguage.ChatBtnAngl);
            Statistic_btn.setText(ThemeAndLanguage.StatisticBtnAngl);
            Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);


            One.setText(ThemeAndLanguage.FirstnameAngl);
            Two.setText(ThemeAndLanguage.LastnameAngl);
            Three.setText(ThemeAndLanguage.GenderAngl);
            Four.setText(ThemeAndLanguage.UsernameAngl);
            Five.setText(ThemeAndLanguage.BalanceAngl);


            UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtnAngl);
            ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtnAngl);
            ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalanceAngl);
            ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePasswordAngl);
        }*/


       /* Kursach.currentUser.TYPE = User.PressedBtnType.InitUser;
        try {
            ClientConnection.writer.writeObject(Kursach.currentUser);
            Kursach.currentUser = (User)ClientConnection.reader.readObject();

            Mail_Field.setText(Kursach.currentUser.MAIL);
            Phonenumber_Field.setText(Kursach.currentUser.PHONENUMBER);
            UserName_Field.setText(Kursach.currentUser.USERNAME);

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) { e.printStackTrace(); }*/




        /*ChangePassword_btn.setOnAction(actionEvent -> {
            try {
                if(!ChangePassword_Field.equals("") && !ChangePassword_Field.equals(Kursach.currentUser.PASSWORD))
                {
                    Kursach.currentUser.TYPE = User.PressedBtnType.ChangePassword;
                    Kursach.currentUser.PASSWORD = ChangePassword_Field.getText().trim();

                    ClientConnection.writer.writeObject(Kursach.currentUser);
                    ChangePassword_Field.setText("РџР°СЂРѕР»СЊ РёР·РјРµРЅРµРЅ");
                }
                else
                {
                    ChangePassword_Field.setPromptText("Р’С‹ РЅРёС‡РµРіРѕ РЅРµ РІРІРµР»Рё Р»РёР±Рѕ РёСЃРїРѕР»СЊР·СѓРµС‚Рµ СЃС‚Р°СЂС‹Р№ РїР°СЂРѕР»СЊ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });*/

       /* UpBalance_btn.setOnAction( actionEvent -> {
            try {
                if (!ChangeBalance_Field.equals("")) {
                    Kursach.ThisUser.TYPE = User.PressedBtnType.AddBalance;
                    Kursach.ThisUser.TempAddBalance = ChangeBalance_Field.getText().trim();
                    ClientConnection.writer.writeObject(Kursach.ThisUser);

                    ChangeBalance_Field.setText("РћР¶РёРґР°Р№С‚Рµ РѕР±СЂР°Р±РѕС‚РєРё Р·Р°РїСЂРѕСЃР°");
                } else {
                    ChangePassword_Field.setPromptText("Р’С‹ РЅРёС‡РµРіРѕ РЅРµ РІРІРµР»Рё");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });*/

       /* Language.setOnAction(actionEvent -> {
            if(Kursach.IsRussionLanguage){
                Kursach.IsRussionLanguage = false;

                Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
                Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
                WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
                Chat_btn.setText(ThemeAndLanguage.ChatBtnAngl);
                Statistic_btn.setText(ThemeAndLanguage.StatisticBtnAngl);
                Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);


                One.setText(ThemeAndLanguage.FirstnameAngl);
                Two.setText(ThemeAndLanguage.LastnameAngl);
                Three.setText(ThemeAndLanguage.GenderAngl);
                Four.setText(ThemeAndLanguage.UsernameAngl);
                Five.setText(ThemeAndLanguage.BalanceAngl);


                UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtnAngl);
                ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtnAngl);
                ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalanceAngl);
                ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePasswordAngl);

            }else{
                Kursach.IsRussionLanguage = true;

                Profile_btn.setText(ThemeAndLanguage.ProfileBtn);
                Product_btn.setText(ThemeAndLanguage.ProductBtn);
                WishList_btn.setText(ThemeAndLanguage.WishlistBtn);
                Chat_btn.setText(ThemeAndLanguage.ChatBtn);
                Statistic_btn.setText(ThemeAndLanguage.StatisticBtn);
                Exit_btn.setText(ThemeAndLanguage.ExitBtn);


                One.setText(ThemeAndLanguage.Firstname);
                Two.setText(ThemeAndLanguage.Lastname);
                Three.setText(ThemeAndLanguage.Gender);
                Four.setText(ThemeAndLanguage.Username);
                Five.setText(ThemeAndLanguage.Balance);


                UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtn);
                ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtn);
                ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalance);
                ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePassword);
            }
        });

        Theme.setOnAction(actionEvent -> {
            if(Kursach.IsLightTheme){
                Kursach.IsLightTheme = false;
                ActorP.setStyle(ThemeAndLanguage.Dark);
            }else{
                Kursach.IsLightTheme = true;
                ActorP.setStyle(ThemeAndLanguage.Light);
            }
        });*/

//        profileButton.setOnAction( actionEvent -> {
//
//            profileButton.getScene().getWindow().hide();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("UserUI.fxml"));
//
//            try {
//                loader.load();
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//            Parent root = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        });
        changePasswordButton.setOnAction(actionEvent -> {
            String newPassword=changePasswordField.getText();
            User userForRequest1=makeUserInfo(User.getCurrentUserLogin(),newPassword,mailField.getText(),false,false);
            Connection.writeObject(userForRequest1, Commands.ChangeUserPassword);

            changePasswordButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/user.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        catalogButton.setOnAction(actionEvent -> {

            catalogButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/carCatalog.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        });
        wishListButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/wishList.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        /*WishList_btn.setOnAction( actionEvent -> {

            WishList_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CartUI.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });*/

        /*Chat_btn.setOnAction( actionEvent -> {

            Chat_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ChatUI.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        Statistic_btn.setOnAction( actionEvent -> {

            Statistic_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StatisticUI.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });*/

        exitButton.setOnAction(actionEvent -> {

            exitButton.getScene().getWindow().hide();
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
            stage.show();
        });
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

