package controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Document;  // ✅
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;


import java.io.IOException;

public class ContractController {

    @FXML private TextField clientNameField;
    @FXML private TextField eventNameField;
    @FXML private DatePicker eventDatePicker;
    @FXML private TextField venueCostField;
    @FXML private TextArea contractDetailsField;

    @FXML
    private void handleCreateContract(ActionEvent event) {
        String clientName = clientNameField.getText().trim();
        String eventName = eventNameField.getText().trim();
        String venueCost = venueCostField.getText().trim();
        String contractDetails = contractDetailsField.getText().trim();

        if (clientName.isEmpty() || eventName.isEmpty() || venueCost.isEmpty()) {
            contractDetailsField.setText("Please fill in all required fields.");
            return;
        }

        try {
            double cost = Double.parseDouble(venueCost);
            // Here you can process saving the contract (e.g., database or file storage)
            contractDetailsField.setText("Contract for " + eventName + " created with " + clientName + ". Venue cost: £" + cost);
        } catch (NumberFormatException e) {
            contractDetailsField.setText("Invalid cost entered.");
        }
    }
    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contract Export");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @FXML
    private void handleBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) clientNameField.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void handleExportContractPDF() {
        try {
            String userHome = System.getProperty("user.home");
            String path = userHome + "/Downloads/Contract_" + eventNameField.getText().replaceAll("\\s+", "_") + ".pdf";

            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("📄 Contract for " + eventNameField.getText())
                    .setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
            document.add(new Paragraph("Client: " + clientNameField.getText()));
            document.add(new Paragraph("Date: " + eventDatePicker.getValue()));
            document.add(new Paragraph("Cost: £" + venueCostField.getText()));
            document.add(new Paragraph("Details:").setBold());
            document.add(new Paragraph(contractDetailsField.getText()));

            document.close();
            showInfo("Contract exported to: " + path);
        } catch (Exception e) {
            showInfo("Failed to export contract.");
            e.printStackTrace();
        }
    }


}
