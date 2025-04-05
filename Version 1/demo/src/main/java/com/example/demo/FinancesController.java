package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FinancesController {

    @FXML
    private TextField expendituresField;

    @FXML
    private TextField profitsField;

    @FXML
    private TextField netField;

    @FXML
    public void initialize() {
        // When either field changes, update the net.
        ChangeListener<String> computeNetListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateNet();
            }
        };
        expendituresField.textProperty().addListener(computeNetListener);
        profitsField.textProperty().addListener(computeNetListener);
    }

    private void updateNet() {
        try {
            double expenditures = Double.parseDouble(expendituresField.getText().trim());
            double profits = Double.parseDouble(profitsField.getText().trim());
            double net = profits - expenditures;
            netField.setText(String.format("%.2f", net));
        } catch (NumberFormatException ex) {
            netField.setText("");
        }
    }
}
