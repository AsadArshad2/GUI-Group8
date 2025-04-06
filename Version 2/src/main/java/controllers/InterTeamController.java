package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class InterTeamController {

    @FXML
    private ListView<String> sharedDataList;

    @FXML
    public void initialize() {
        sharedDataList.setItems(FXCollections.observableArrayList(
                "Marketing Request: Hold Main Hall on Apr 28",
                "Box Office: 285 tickets sold for Hamlet",
                "Marketing Feedback: Need promo space May 3"
        ));
    }

    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
