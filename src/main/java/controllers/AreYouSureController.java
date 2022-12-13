package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AreYouSureController {
    @FXML
    private Label textArea;

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private Button confirmButton;

    private final ObservableList<String>choices= FXCollections.observableArrayList("Да","Нет");

    @FXML
    void initialize()
    {
        choice.setItems(choices);
        choice.setValue("Нет");

        confirmButton.setOnAction(actionEvent -> {
            String decision=choice.getValue();
            if(decision.equals("Да"))
            {
                User userForRequest = makeUserInfo(User.getCurrentUserLogin(),String.valueOf(Car.getCurrentCarId()), "",  false, false);
                Connection.writeObject(userForRequest, Commands.AddOrder);
            }
            confirmButton.getScene().getWindow().hide();
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
