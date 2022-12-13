package controllers;

import commands.Commands;
import connection.Connection;
import entities.Car;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewCarController {
    @FXML
    private Button applyButton;

    @FXML
    private Label vendorLabel;

    @FXML
    private TextField vendorField;

    @FXML
    private Label modelLabel;

    @FXML
    private TextField modelField;

    @FXML
    private Label yearLabel;

    @FXML
    private TextField yearField;
    @FXML
    private Label transmissionLabel;

    @FXML
    private ChoiceBox<String> transmissionChoiceBox;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceField;

    @FXML
    private Label engineVolumeLabel;

    @FXML
    private ChoiceBox<Integer> engineVolumeChoiceBox;

    private final ObservableList<String> choicesForTransmission = FXCollections.observableArrayList("Auto", "Mechanic");

    private final ObservableList<Integer> choicesForEngineVolume = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

    @FXML
    void initialize() {
        transmissionChoiceBox.setItems(choicesForTransmission);
        transmissionChoiceBox.setValue("Mechanic");

        engineVolumeChoiceBox.setItems(choicesForEngineVolume);
        engineVolumeChoiceBox.setValue(5);

        if (AdminController.isUpdatingFlag()) {
            vendorField.setText(Car.getCurrentCarVendor());
            modelField.setText(Car.getCurrentCarModel());
            engineVolumeChoiceBox.setValue(Integer.parseInt(Car.getCurrentCarVolume()));
            transmissionChoiceBox.setValue(Car.getCurrentCarTransmission());
            priceField.setText(String.valueOf(Car.getCurrentCarPrice()));
            yearField.setText(Car.getCurrentCarYearOfIssue());
        }

        applyButton.setOnAction(actionEvent -> {
            Car car = new Car();
            if (!priceField.getText().isEmpty())
                car.setPrice(new Long(priceField.getText()));
            if (!vendorField.getText().isEmpty())
                car.setVendor(vendorField.getText());
            if (!modelField.getText().isEmpty())
                car.setModel(modelField.getText());
            if (!yearField.getText().isEmpty())
                car.setYearOfIssue(yearField.getText());
            car.setTransmission(transmissionChoiceBox.getValue());
            car.setEngineVolume(String.valueOf(engineVolumeChoiceBox.getValue()));
            car.setId(Car.getCurrentCarId());

            if(!AdminController.isUpdatingFlag())
                Connection.writeObject(car, Commands.AddCar);
            else {
                Connection.writeCarWithId(car, Commands.UpdateCar);
                AdminController.setUpdatingFlag(false);
            }

            applyButton.getScene().getWindow().hide();
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
    }


}
