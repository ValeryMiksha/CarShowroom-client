package controllers;

import commands.Commands;
import connection.Connection;
import entities.Comment;
import entities.DtoLoginComment;
import entities.MessageToAdmin;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MessagesController {
    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<MessageToAdmin,String>userLogin;

    @FXML
    private TableColumn<MessageToAdmin,String> content;
    @FXML
    private TableView<MessageToAdmin> productTable;

    @FXML
    private Button profileButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label label;
    private ObservableList<MessageToAdmin> list = FXCollections.observableArrayList();

    private final int ALL_MESSAGES_SENT=7;

    @FXML
    void initialize()
    {
        userLogin.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getUserLogin()));
        content.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getContent()));
        User userToBlock = makeUserInfo("", "", "", false, false);

        Connection.writeObject(userToBlock, Commands.GetMessages);

        MessageToAdmin newMessage;

        while (true) {
            String info = Connection.readObject();
            if (info != null) {
                JSONObject obj = new JSONObject(info);
                Map<String, Object> parseMap = obj.toMap();
                int command = (int) parseMap.get("command");
                if (command == ALL_MESSAGES_SENT) {
                    for (Map.Entry<String, Object> temp : parseMap.entrySet()) {
                        if (temp.getKey().equals("command"))
                            break;
                        newMessage = new MessageToAdmin();
                        newMessage.setUserLogin(temp.getKey());
                        ArrayList<HashMap<String, Object>> aaa = (ArrayList<HashMap<String, Object>>) temp.getValue();
                        for (int i = 0; i < aaa.size(); i++) {
                            HashMap<String, Object> currMap = aaa.get(i);
                            MessageToAdmin finalMessage = new MessageToAdmin();
                            currMap.forEach((k, v) -> {
                                if (v instanceof String) {
                                    if (k.equals("content"))
                                        finalMessage.setContent(v.toString());
                                    if (k.equals("userLogin"))
                                        finalMessage.setUserLogin(v.toString());
                                }
                            });
                            newMessage = finalMessage;
                            list.add(newMessage);
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
