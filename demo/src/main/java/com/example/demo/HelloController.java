package com.example.demo;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private HBox initialBox;   // Contains the initial horizontal menu buttons.
    @FXML
    private HBox menuBar;      // Animated top menu bar.
    @FXML
    private Label displayText; // Displays which menu option is selected.

    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        // Immediately update the display text.
        displayText.setText(buttonText + " Selected");
        displayText.setVisible(true);

        // If the menuBar isn't populated yet, animate the buttons into it.
        if (menuBar.getChildren().isEmpty()) {
            Scene scene = initialBox.getScene();
            if (scene == null) {
                return; // Scene not available.
            }
            double sceneWidth = scene.getWidth();
            int n = initialBox.getChildren().size();
            double allocatedWidth = sceneWidth / n;
            double finalY = 10; // Final Y coordinate for the menu bar.

            // Create a copy of the buttons.
            List<Node> buttons = new ArrayList<>(initialBox.getChildren());
            ParallelTransition parallelTransition = new ParallelTransition();

            for (int i = 0; i < n; i++) {
                Node node = buttons.get(i);
                if (node instanceof Button) {
                    Button btn = (Button) node;
                    double btnWidth = btn.getLayoutBounds().getWidth();
                    // Calculate the target X so that the button is centered in its allocated slice.
                    double targetX = allocatedWidth * i + allocatedWidth / 2 - btnWidth / 2;
                    double targetY = finalY;
                    Point2D startPos = btn.localToScene(0, 0);
                    double deltaX = targetX - startPos.getX();
                    double deltaY = targetY - startPos.getY();

                    TranslateTransition tt = new TranslateTransition(Duration.millis(600), btn);
                    tt.setByX(deltaX);
                    tt.setByY(deltaY);
                    tt.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
                    parallelTransition.getChildren().add(tt);
                }
            }

            parallelTransition.setOnFinished(e -> {
                // Reparent the buttons into the menu bar.
                for (Node node : buttons) {
                    if (node instanceof Button) {
                        Button btn = (Button) node;
                        btn.setTranslateX(0);
                        btn.setTranslateY(0);
                        btn.setMaxWidth(Double.MAX_VALUE);
                        HBox.setHgrow(btn, Priority.ALWAYS);
                        menuBar.getChildren().add(btn);
                    }
                }
                initialBox.getChildren().clear();
                menuBar.setVisible(true);
                updateSelectedButton(clickedButton);
                navigateBasedOnSelection(buttonText);
            });
            parallelTransition.play();
        } else {
            // Menu bar is already visible: update style and navigate.
            updateSelectedButton(clickedButton);
            navigateBasedOnSelection(buttonText);
        }
    }

    /**
     * Updates the style of the buttons in the menu bar so that the selected button is highlighted.
     */
    private void updateSelectedButton(Button selected) {
        for (Node node : menuBar.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                if (btn.equals(selected)) {
                    // Selected style.
                    btn.setStyle("-fx-background-color: #1ABC9C; -fx-text-fill: white;");
                } else {
                    // Default style.
                    btn.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;");
                }
            }
        }
    }

    /**
     * Navigates based on the selected menu option.
     */
    private void navigateBasedOnSelection(String selection) {
        if ("Bookings".equals(selection)) {
            navigateToBookingScreen();
        } else if ("Calendar".equals(selection)) {
            navigateToCalendarScreen();
        } else if ("Finances".equals(selection)) {
            navigateToFinancesScreen();
        } else if ("Daily Sheet".equals(selection)) {
            navigateToDailySheetScreen();
        } else if ("Reviews".equals(selection)) {
            navigateToReviewScreen();
        } else if ("Logout".equals(selection)) {
            handleLogout();
        }
    }

    private void navigateToBookingScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/booking.fxml"));
            Parent bookingForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(bookingForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToCalendarScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/calendar.fxml"));
            Parent calendarForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(calendarForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToFinancesScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/finances.fxml"));
            Parent financesForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(financesForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToDailySheetScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/dailySheet.fxml"));
            Parent dailySheetForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(dailySheetForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToReviewScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/review.fxml"));
            Parent reviewForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(reviewForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the application when Logout is selected.
     */
    private void handleLogout() {
        try {
            // Load login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Login.fxml"));
            Parent loginRoot = loader.load();

            // Get current stage from any node
            Stage stage = (Stage) displayText.getScene().getWindow();

            // Replace the scene with a new login scene
            Scene loginScene = new Scene(loginRoot);
            stage.setScene(loginScene);

            // Optional: reset title and center window
            stage.setTitle("Staff Login");
            stage.centerOnScreen();

            // Optional: resize to fit login layout
            stage.sizeToScene();

            System.out.println("Logged out and returned to login screen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void handleLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout.fxml"));
            Parent reviewForm = loader.load();
            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
            rootPane.setCenter(reviewForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}

