package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SeatingController {

    public void openMainHall(ActionEvent event) throws Exception {
        switchScene("MainHall.fxml", event);
    }

    public void openSmallHall(ActionEvent event) throws Exception {
        switchScene("SmallHall.fxml", event);
    }

    public void handleBackToMenu(ActionEvent event) throws Exception {
        switchScene("Menu.fxml", event);
    }

    private void switchScene(String fxmlFile, ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + fxmlFile));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
