package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import models.Seat;

import java.io.IOException;

public class SeatingController {
    private boolean layoutFinalized = false;

    @FXML
    private ComboBox<String> roomSelector;

    @FXML
    private TableView<Seat> seatingTable;

    @FXML
    private TableColumn<Seat, String> rowColumn;

    @FXML
    private TableColumn<Seat, String> seatColumn;

    @FXML
    private TableColumn<Seat, Boolean> viewColumn;

    @FXML
    private TableColumn<Seat, Boolean> accessColumn;

    @FXML
    public void initialize() {
        rowColumn.setCellValueFactory(new PropertyValueFactory<>("row"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("restrictedView"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("wheelchairAccessible"));

        roomSelector.setItems(FXCollections.observableArrayList("Main Hall", "Small Hall", "Rehearsal Room"));
        roomSelector.setOnAction(e -> loadSeatingData(roomSelector.getValue()));
    }

    private void loadSeatingData(String roomName) {
        // Replace with real data source
        ObservableList<Seat> dummySeats = FXCollections.observableArrayList(
                new Seat("A", "1", false, true),
                new Seat("A", "2", true, false),
                new Seat("B", "1", false, false)
        );
        seatingTable.setItems(dummySeats);
    }

    @FXML
    private void handleSave() {
        // TODO: Save seating data to database or file
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Seating Configurations");
        alert.setHeaderText(null);
        alert.setContentText("Seating configuration saved successfully.");
        alert.showAndWait();
    }

    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void handleFinalizeLayout() {
        layoutFinalized = true;
        seatingTable.setDisable(true);
        roomSelector.setDisable(true);
        showAlert("Seating layout has been finalized and locked.");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Seating Finalized");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
