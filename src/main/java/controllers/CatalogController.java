package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.User;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CatalogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button commentButton;

    @FXML
    private Button profileButton;

    @FXML
    private TableView<Car> productTable;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> company;

    @FXML
    private TableColumn<Car, String> model;

    @FXML
    private TableColumn<Car, String> cost;

    @FXML
    private TableColumn<Car, String> year;

    @FXML
    private TableColumn<Car, String> volume;

    private ObservableList<Car> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane pane;

    private int ALL_CARS_SENT = 2;

    @FXML
    void initialize() {
        id.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getId())));
        model.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getModel()));
        year.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getYearOfIssue()));
        cost.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPrice())));
        company.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getVendor()));
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

        commentButton.setOnAction(actionEvent ->
        {
            if(productTable.getSelectionModel().getSelectedItem()!=null) {
                Car.setCurrentCarId(productTable.getSelectionModel().getSelectedItem().getId());
                Car.setCurrentCarModel(productTable.getSelectionModel().getSelectedItem().getModel());
                Car.setCurrentCarVendor(productTable.getSelectionModel().getSelectedItem().getVendor());
                Car.setCurrentCarPrice(productTable.getSelectionModel().getSelectedItem().getPrice());
                Car.setCurrentCarVolume(productTable.getSelectionModel().getSelectedItem().getEngineVolume());
                Car.setCurrentCarYearOfIssue(productTable.getSelectionModel().getSelectedItem().getYearOfIssue());

                commentButton.getScene().getWindow().hide();
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
                stage.show();
            }

        });



        /*if(Kursach.IsLightTheme)
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

            AddToCart_btn.setText(ThemeAndLanguage.AddToCartBtn);
            AddComment_btn.setText(ThemeAndLanguage.AddReviewBtn);
            AddGrade_btn.setText(ThemeAndLanguage.AddGradeBtn);
            CheckComments_btn.setText(ThemeAndLanguage.CheckReviewBtn);
            Cheap_btn.setText(ThemeAndLanguage.BudgetBtn);
            Expensive_btn.setText(ThemeAndLanguage.LuxuryBtn);


        }else {
            Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
            Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
            Chat_btn.setText(ThemeAndLanguage.ChatBtnAngl);
            Statistic_btn.setText(ThemeAndLanguage.StatisticBtnAngl);
            Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);

            AddToCart_btn.setText(ThemeAndLanguage.AddToCartBtnAngl);
            AddComment_btn.setText(ThemeAndLanguage.AddReviewBtnAngl);
            AddGrade_btn.setText(ThemeAndLanguage.AddGradeBtnAngl);
            CheckComments_btn.setText(ThemeAndLanguage.CheckReviewBtnAngl);
            Cheap_btn.setText(ThemeAndLanguage.BudgetBtnAngl);
            Expensive_btn.setText(ThemeAndLanguage.LuxuryBtnAngl);
        }*/

       /* id.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        company.setCellValueFactory(cellValue -> cellValue.getValue().Company);
        model.setCellValueFactory(cellValue -> cellValue.getValue().Model);
        cost.setCellValueFactory(cellValue->cellValue.getValue().Cost);
        year.setCellValueFactory(cellValue->cellValue.getValue().YearOfIssue);
        valume.setCellValueFactory(cellValue->cellValue.getValue().ValumeOfEngine);

        productTable.setEditable(true);

        User user = new User();
        user.TYPE = User.PressedBtnType.AddProductTable;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            productTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User) ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.Company, user.Model, user.Cost, user.YearOfIssue, user.ValumeOfEngine);
                list.add(car);
            }

            productTable.setItems(list);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
////////////////////////////////////


        /*AddGrade_btn.setOnAction( actionEvent -> {

            String grade = Grade.getText().trim();
            if(grade.equals("1") || grade.equals("2") || grade.equals("3") || grade.equals("4") || grade.equals("5"))
            {
                User UserGrade = new User();
                UserGrade.TempAddBalance = grade;
                Car car = productTable.getSelectionModel().getSelectedItem();
                UserGrade.idProducts = car.getIdProducts();
                UserGrade.TYPE = User.PressedBtnType.AddGrade;
                try {
                    ClientConnection.writer.writeObject(UserGrade);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        AddToCart_btn.setOnAction( actionEvent -> {

            Car car = productTable.getSelectionModel().getSelectedItem();

            User BuyUser = new User();
            BuyUser.TYPE = User.PressedBtnType.AddToCart;
            BuyUser.id = Kursach.id;
            BuyUser.idProducts = car.getIdProducts();

            try {
                ClientConnection.writer.writeObject(BuyUser);
            } catch (IOException e){
                e.printStackTrace();
            }
        });


        AddComment_btn.setOnAction( actionEvent -> {

            Car car = productTable.getSelectionModel().getSelectedItem();
            if(!car.getIdProducts().equals(null)){
                if(!Review_Field.getText().equals(null)) {
                    User Comment = new User();
                    Comment.id = Kursach.id;
                    Comment.idProducts = car.getIdProducts();
                    Comment.TempAddBalance = Review_Field.getText().trim();
                    Comment.TYPE = User.PressedBtnType.Comment;
                    try {
                        ClientConnection.writer.writeObject(Comment);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("РЎС‚СЂРѕРєР° РѕС‚Р·С‹РІР° РїСѓСЃС‚Р°");
                }

            } else
                System.out.println("Р’С‹ РЅРµ РІС‹Р±СЂР°Р»Рё С‚РѕРІР°СЂ");

        });

        CheckComments_btn.setOnAction(actionEvent -> {
            CheckComments_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CommentUI.fxml"));

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



        Cheap_btn.setOnAction( actionEvent -> {
            list.clear();
            User Sort = new User();
            Sort.TYPE = User.PressedBtnType.CheapSort;
            try {
                ClientConnection.writer.writeObject(Sort);
                Sort = (User) ClientConnection.reader.readObject();
                int size = Sort.size;

                productTable.getItems().clear();
                for (int i = 0; i < size; ++i) {
                    Sort = (User) ClientConnection.reader.readObject();
                    Car car = new Car(Sort.idProducts, Sort.CarName, Sort.Segment, Sort.Cost, Sort.Count, Sort.Grade);
                    list.add(car);
                }
                productTable.setItems(list);
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        });

        Expensive_btn.setOnAction( actionEvent -> {
            list.clear();
            User Sort = new User();
            Sort.TYPE = User.PressedBtnType.ExpensiveSort;
            try {
                ClientConnection.writer.writeObject(Sort);
                Sort = (User) ClientConnection.reader.readObject();
                int size = Sort.size;

                productTable.getItems().clear();
                for (int i = 0; i < size; ++i) {
                    Sort = (User) ClientConnection.reader.readObject();
                    Car car = new Car(Sort.idProducts, Sort.CarName, Sort.Segment, Sort.Cost, Sort.Count, Sort.Grade);
                    list.add(car);
                }
                productTable.setItems(list);
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        } );*/





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

       /* WishList_btn.setOnAction( actionEvent -> {

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
        });

        Chat_btn.setOnAction( actionEvent -> {

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
