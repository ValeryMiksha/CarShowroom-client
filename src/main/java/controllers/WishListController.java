package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.User;
import entities.Wishlist;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class WishListController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField messageField;

    @FXML
    private Button exitButton;

    @FXML
    private Button productButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button messageButton;

    @FXML
    private TableColumn<Car, String> company;

    @FXML
    private TableColumn<Car, String> cost;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> model;

    @FXML
    private TableView<Car> productTable;

    @FXML
    private TableColumn<Car, String> volume;

    @FXML
    private TableColumn<Car, String> year;

    private ObservableList<Car>list= FXCollections.observableArrayList();

    private final int GET_USER_WISHLIST=4;
    private final int NO_USER_WISHLIST=9;

    @FXML
    void initialize() {
        id.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getId())));
        model.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getModel()));
        year.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getYearOfIssue()));
        cost.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPrice())));
        company.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getVendor()));
        volume.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEngineVolume()));

        Car var=null;

        User userForRequest = makeUserInfo(User.getCurrentUserLogin(), "", "", false, false);
        Connection.writeObject(userForRequest, Commands.GetUserWishList);

        while (true) {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if (command == GET_USER_WISHLIST) {
                    for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                        if(temp.getKey().equals("command"))
                            break;
                        var = new Car();
                        var.setModel(temp.getKey());
                        ArrayList<HashMap<String, Object>> aaa = (ArrayList<HashMap<String, Object>>) temp.getValue();
                        for (int i = 0; i < aaa.size(); i++)
                        {
                            HashMap<String, Object> currMap = aaa.get(i);
                            Car finalVar = var;
                            currMap.forEach((k, v)->{
                                if (v instanceof String)
                                {
                                    if (k.equals("transmission"))
                                        finalVar.setTransmission(v.toString());
                                    if (k.equals("engineVolume"))
                                        finalVar.setEngineVolume(v.toString());
                                    if (k.equals("vendor"))
                                        finalVar.setVendor(v.toString());
                                    if (k.equals("yearOfIssue"))
                                        finalVar.setYearOfIssue(v.toString());
                                }
                                else if(v instanceof Integer)
                                {
                                    if (k.equals("id"))
                                        finalVar.setId((Integer) v);
                                    if (k.equals("price"))
                                        finalVar.setPrice(new Long((Integer) v));
                                }
                            });
                            var=finalVar;
                        }
                        list.add(var);
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
            stage.show();


        });

        messageButton.setOnAction(actionEvent -> {
            if(!messageField.getText().isEmpty())
            {
                String message=messageField.getText();
                User userForRequest1 = makeUserInfo(User.getCurrentUserLogin(), message, "", false, false);
                Connection.writeObject(userForRequest1, Commands.AddMessage);

                messageField.clear();
            }
        });

        productButton.setOnAction(actionEvent -> {
            productButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/carCatalog.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
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
