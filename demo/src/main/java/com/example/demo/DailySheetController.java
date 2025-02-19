package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailySheetController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label eventLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label seatsLabel;

    @FXML
    private Label venueLabel;

    @FXML
    public void initialize() {
        // Set today's date in a nice format.
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        dateLabel.setText(today.format(formatter));

        // For demonstration, set dummy data for the remaining fields.
        // In a real application, you might retrieve this data from a database or previous screen.
        eventLabel.setText("Annual Gala");
        timeLabel.setText("7:00 PM");
        seatsLabel.setText("150");
        venueLabel.setText("Grand Ballroom, Hotel XYZ");
    }
}
