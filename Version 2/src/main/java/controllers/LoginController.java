package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginStatus;

    @FXML
    private void handleLogin() {
        if ("".equals(usernameField.getText()) && "".equals(passwordField.getText())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                loginStatus.setText("Error loading menu.");
                e.printStackTrace();
            }
        } else {
            loginStatus.setText("Invalid credentials.");
        }
    }
}
