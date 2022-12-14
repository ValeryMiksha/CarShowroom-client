package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.Comment;
import entities.DtoLoginComment;
import entities.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommentsController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button exitButton;

    @FXML
    private Button addCommentButton;

    @FXML
    private Button profileButton;
    @FXML
    private Button addToWishListButton;
    @FXML
    private Button orderButton;

    @FXML
    private TableColumn<DtoLoginComment, String> comment;

    @FXML
    private Label companyAndModelLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private TableColumn<DtoLoginComment, String> login;

    @FXML
    private Label modelLabel;

    @FXML
    private TableView<DtoLoginComment> productTable;

    @FXML
    private Label volumeLabel;

    @FXML
    private Label yearLabel;
    private ObservableList<DtoLoginComment> list = FXCollections.observableArrayList();

    private final int CAR_COMMENTS_SENT=3;
    private final int USER_INFO_GET=1;

    @FXML
    void initialize() {
        companyAndModelLabel.setText(Car.getCurrentCarVendor()+"   "+Car.getCurrentCarModel());
        companyLabel.setText(Car.getCurrentCarVendor());
        priceLabel.setText(String.valueOf(Car.getCurrentCarPrice()));
        modelLabel.setText(Car.getCurrentCarModel());
        volumeLabel.setText(Car.getCurrentCarVolume());
        yearLabel.setText(Car.getCurrentCarYearOfIssue());

        login.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getLogin())));
        comment.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getComment()));

        User userForRequest = makeUserInfo(String.valueOf(Car.getCurrentCarId()), "", "", false, false);
        Connection.writeObject(userForRequest, Commands.GetCarComments);

        Comment ccomment;
        User userInfo;
        DtoLoginComment dto=null;

        while (true) {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if (command == CAR_COMMENTS_SENT) {
                    for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                        if(temp.getKey().equals("command"))
                            break;
                        ccomment = new Comment();
                        ccomment.setCarId(Integer.parseInt(temp.getKey()));
                        ArrayList<HashMap<String, Object>> aaa = (ArrayList<HashMap<String, Object>>) temp.getValue();
                        for (int i = 0; i < aaa.size(); i++)
                        {
                            HashMap<String, Object> currMap = aaa.get(i);
                            Comment finalCcomment = ccomment;
                            currMap.forEach((k, v)->{
                                if (v instanceof String)
                                {
                                    if (k.equals("content"))
                                        finalCcomment.setContent(v.toString());
                                }
                                else if(v instanceof Integer)
                                {
                                    if (k.equals("userId"))
                                        finalCcomment.setUserId((Integer) v);
                                    if (k.equals("carId"))
                                        finalCcomment.setCarId((Integer) v);
                                    if (k.equals("id"))
                                        finalCcomment.setId((Integer) v);
                                }
                            });
                            ccomment=finalCcomment;
                            dto=new DtoLoginComment();
                            dto.setComment(ccomment.getContent());
                            userForRequest=makeUserInfo(String.valueOf(ccomment.getUserId()),"","",false,false);
                            Connection.writeObject(userForRequest, Commands.GetUserInfoId);

                            while(true)
                            {
                                info = Connection.readObject();
                                if (info != null) {
                                    obj = new JSONObject(info);
                                    parseMap = obj.toMap();
                                    command = (int) parseMap.get("command");
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
                            dto.setLogin(userInfo.getLogin());
                            list.add(dto);
                        }
                    }
                }
                break;
            }
        }

        productTable.setItems(list);


        profileButton.setOnAction(actionEvent -> {

            profileButton.getScene().getWindow().hide();
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

        });

        exitButton.setOnAction(actionEvent ->

        {
            exitButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home.fxml"));

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


        });

        addCommentButton.setOnAction(actionEvent -> {
            addCommentButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/commentForm.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Комменарий");
            stage.setResizable(false);
            stage.show();

        });

        addToWishListButton.setOnAction(actionEvent -> {
            User userForRequest1 = makeUserInfo(String.valueOf(Car.getCurrentCarId()), String.valueOf(User.getCurrentUserLogin()), "", false, false);
            Connection.writeObject(userForRequest1, Commands.AddCarToUserWishlist);
        });


        orderButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fillerFromRequest.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Заказ");
            stage.setResizable(false);
            stage.show();

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
