package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarController {

    @FXML
    private Button prevMonthButton;

    @FXML
    private Button nextMonthButton;

    @FXML
    private Label monthYearLabel;

    @FXML
    private GridPane calendarGrid;

    private YearMonth currentYearMonth;

    @FXML
    public void initialize() {
        // Start with the current month.
        currentYearMonth = YearMonth.now();
        updateCalendar();

        prevMonthButton.setOnAction(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalendar();
        });

        nextMonthButton.setOnAction(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalendar();
        });
    }

    private void updateCalendar() {
        // Update the header label.
        String monthName = currentYearMonth.getMonth().toString();
        int year = currentYearMonth.getYear();
        monthYearLabel.setText(monthName + " " + year);

        // Clear any existing calendar content.
        calendarGrid.getChildren().clear();

        // Determine the day of week for the first day of the month.
        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeekOfFirst = firstOfMonth.getDayOfWeek().getValue(); // Monday=1, ... Sunday=7.
        // For a calendar starting on Sunday, convert Sunday to 0.
        int startColumn = (dayOfWeekOfFirst % 7);

        int totalDays = currentYearMonth.lengthOfMonth();
        int row = 0;
        int col = startColumn;
        for (int day = 1; day <= totalDays; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setTextFill(Color.WHITE);
            dayLabel.setFont(new Font(16));
            calendarGrid.add(dayLabel, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
}
