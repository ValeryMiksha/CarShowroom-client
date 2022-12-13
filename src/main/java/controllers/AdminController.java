package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.DtoLoginComment;
import entities.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Button addButton;

    @FXML
    private Button blockButton;

    @FXML
    private TextField companyField;

    @FXML
    private Label carName;

    @FXML
    private TextField priceField;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField yearField;

    @FXML
    private Label yearLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private Button messagesButton;
    @FXML
    private Button exitButton;

    @FXML
    private Label modelLabel;

    @FXML
    private Label volumeLabel;

    @FXML
    private TextField modelField;

    @FXML
    private TextField volumeField;


    ///////////////////////////////////
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> userId;

    @FXML
    private TableColumn<User, String> userLogin;

    @FXML
    private TableColumn<User, String> locked;


    ////////////////////////////////////
    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> vendor;

    @FXML
    private TableColumn<Car, String> model;

    @FXML
    private TableColumn<Car, String> price;

    @FXML
    private TableColumn<Car, String> year;

    @FXML
    private TableColumn<Car, String> volume;

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private int ALL_CARS_SENT = 2;
    private int ALL_USERS_SENT = 5;
    private int STATISTICS_SENT = 6;

    private static boolean updatingFlag = false;

    public static boolean isUpdatingFlag() {
        return updatingFlag;
    }

    public static void setUpdatingFlag(boolean updatingFlag) {
        AdminController.updatingFlag = updatingFlag;
    }

    @FXML
    void initialize() {

        id.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getId())));
        model.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getModel()));
        year.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getYearOfIssue()));
        price.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPrice())));
        vendor.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getVendor()));
        volume.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEngineVolume()));

        Car var = null;

        User userForRequest = makeUserInfo("", "", "", false, false);
        Connection.writeObject(userForRequest, Commands.GetAllCars);

        while (true) {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if (command == ALL_CARS_SENT) {
                    for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                        if (temp.getKey().equals("command"))
                            break;
                        var = new Car();
                        var.setModel(temp.getKey());
                        ArrayList<HashMap<String, Object>> aaa = (ArrayList<HashMap<String, Object>>) temp.getValue();
                        for (int i = 0; i < aaa.size(); i++) {
                            HashMap<String, Object> currMap = aaa.get(i);
                            Car finalVar = var;
                            currMap.forEach((k, v) -> {
                                if (v instanceof String) {
                                    if (k.equals("transmission"))
                                        finalVar.setTransmission(v.toString());
                                    if (k.equals("engineVolume"))
                                        finalVar.setEngineVolume(v.toString());
                                    if (k.equals("vendor"))
                                        finalVar.setVendor(v.toString());
                                    if (k.equals("yearOfIssue"))
                                        finalVar.setYearOfIssue(v.toString());
                                } else if (v instanceof Integer) {
                                    if (k.equals("id"))
                                        finalVar.setId((Integer) v);
                                    if (k.equals("price"))
                                        finalVar.setPrice(new Long((Integer) v));
                                }
                            });
                            var = finalVar;
                        }
                        carList.add(var);
                    }
                }
                break;
            }
        }

        carsTable.setItems(carList);

        userId.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getId())));
        userLogin.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLogin()));
        locked.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().isLocked())));

        User var1 = null;

        userForRequest = makeUserInfo("", "", "", false, false);
        Connection.writeObject(userForRequest, Commands.GetAllUsers);

        while (true) {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if (command == ALL_USERS_SENT) {
                    for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                        if (temp.getKey().equals("command"))
                            break;
                        var1 = new User();
                        var1.setLogin(temp.getKey());
                        ArrayList<HashMap<String, Object>> aaa = (ArrayList<HashMap<String, Object>>) temp.getValue();
                        for (int i = 0; i < aaa.size(); i++) {
                            HashMap<String, Object> currMap = aaa.get(i);
                            User finalVar = var1;
                            currMap.forEach((k, v) -> {
                                if (v instanceof String) {
                                    if (k.equals("password"))
                                        finalVar.setPassword(v.toString());
                                    if (k.equals("email"))
                                        finalVar.setEmail(v.toString());
                                } else if (v instanceof Boolean) {
                                    if (k.equals("locked"))
                                        finalVar.setLocked((Boolean) v);
                                    if (k.equals("admin"))
                                        finalVar.setAdmin((Boolean) v);
                                } else if (v instanceof Integer) {
                                    if (k.equals("id"))
                                        finalVar.setId((Integer) v);
                                }
                            });
                            var1 = finalVar;
                        }
                        userList.add(var1);
                    }
                }
                break;
            }
        }

        userTable.setItems(userList);


        updateButton.setOnAction(actionEvent -> {
            if(carsTable.getSelectionModel().getSelectedItem()!=null) {
                Car selectedCar = carsTable.getSelectionModel().getSelectedItem();
                Car.setCurrentCarPrice(selectedCar.getPrice());
                Car.setCurrentCarVolume(selectedCar.getEngineVolume());
                Car.setCurrentCarModel(selectedCar.getModel());
                Car.setCurrentCarYearOfIssue(selectedCar.getYearOfIssue());
                Car.setCurrentCarVendor(selectedCar.getVendor());
                Car.setCurrentCarTransmission(selectedCar.getTransmission());
                Car.setCurrentCarId(selectedCar.getId());
                AdminController.setUpdatingFlag(true);
                updateButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/newCar.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }


        });

        deleteButton.setOnAction(actionEvent -> {
            if(carsTable.getSelectionModel().getSelectedItem()!=null) {
                Car selectedCar = carsTable.getSelectionModel().getSelectedItem();
                Connection.writeCarWithId(selectedCar, Commands.DeleteCar);

                deleteButton.getScene().getWindow().hide();
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
                stage.show();
            }
        });

        blockButton.setOnAction(actionEvent -> {
            User userToBlock = userTable.getSelectionModel().getSelectedItem();
            if(userToBlock!=null) {

                Connection.writeObject(userToBlock, Commands.ChangeUserStatus);

                blockButton.getScene().getWindow().hide();
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
                stage.show();
            }
        });


        statisticsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            User userToBlock = makeUserInfo("", "", "", false, false);

            Connection.writeObject(userToBlock, Commands.GetStatistics);

            DtoLoginComment var2 = null;
            List<DtoLoginComment> statistics = new ArrayList<>();

            while (true) {
                String info = Connection.readObject();
                if (info != null) {
                    JSONObject obj = new JSONObject(info);
                    Map<String, Object> parseMap = obj.toMap();
                    int command = (int) parseMap.get("command");
                    if (command == STATISTICS_SENT) {
                        for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                            if (temp.getKey().equals("command"))
                                break;
                            var2 = new DtoLoginComment();
                            var2.setLogin(temp.getKey());
                            if (temp.getValue() instanceof String)
                                var2.setComment(temp.getValue().toString());
                            statistics.add(var2);
                        }

                    }
                }
                break;
            }
            try (FileWriter writer = new FileWriter("statistics.txt", false)) {
                for (DtoLoginComment temp : statistics) {
                    String login = temp.getLogin();
                    String content = temp.getComment();
                    writer.write(login + '|');
                    writer.write(content + '\n');
                }

                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        });

        exitButton.setOnAction(actionEvent -> {

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

        messagesButton.setOnAction(actionEvent -> {
            messagesButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/messages.fxml"));

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


        refreshButton.setOnAction(actionEvent ->

        {
            refreshButton.getScene().getWindow().hide();
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
            stage.show();
        });

        addButton.setOnAction(actionEvent ->
        {
            addButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/newCar.fxml"));

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
