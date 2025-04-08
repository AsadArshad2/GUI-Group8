package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SmallHallController {

    public void toggleSeat(ActionEvent event) {
        Button seat = (Button) event.getSource();
        if (seat.getStyle().contains("f44336")) {
            seat.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            seat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        }
    }

    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Seating.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
