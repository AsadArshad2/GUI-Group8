package controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import com.itextpdf.layout.Document;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyPlannerController {

    @FXML
    private Label dateLabel;

    @FXML
    private TableView<DailyScheduleEntry> dailyScheduleTable;

    @FXML
    private TableColumn<DailyScheduleEntry, String> roomColumn;

    @FXML
    private TableColumn<DailyScheduleEntry, String> timeColumn;

    @FXML
    private TableColumn<DailyScheduleEntry, String> activityColumn;

    @FXML
    private TableColumn<DailyScheduleEntry, String> bookedByColumn;

    @FXML
    private TableColumn<DailyScheduleEntry, String> configurationColumn;

    @FXML
    private TableColumn<DailyScheduleEntry, String> notesColumn;

    @FXML
    private ListView<String> scheduleList;

    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        dateLabel.setText("Today is " + today.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));

        ObservableList<String> schedule = FXCollections.observableArrayList(
                "Main Hall | 10:00 - 13:00 | School Play Setup",
                "Small Hall | 09:00 - 11:00 | Marketing Meeting",
                "Room 2    | 14:00 - 17:00 | Client Tour",
                "Main Hall | 15:00 - 18:00 | Film: Hamlet (Stalls Only)"
        );

        scheduleList.setItems(schedule);
    }


    private void loadTodaySchedule() {
        ObservableList<DailyScheduleEntry> schedule = FXCollections.observableArrayList(
                new DailyScheduleEntry("Main Hall", "10:00 - 13:00", "School Play Setup", "Operations", "Theatre Layout", "Wheelchair access front rows"),
                new DailyScheduleEntry("Small Hall", "09:00 - 11:00", "Team Meeting", "Marketing", "Boardroom", "Projector required"),
                new DailyScheduleEntry("Room 2", "14:00 - 17:00", "Client Tour", "Operations", "Open Floor", "No setup needed"),
                new DailyScheduleEntry("Main Hall", "15:00 - 18:00", "Film Showing: Hamlet", "Marketing", "Cinema Layout", "Only stalls used (~285 seats)")
        );

        dailyScheduleTable.setItems(schedule);
    }

    @FXML
    private void exportAsPDF() {
        try {
            String userHome = System.getProperty("user.home");
            String path = userHome + "/Downloads/DailyPlanner_" + LocalDate.now() + ".pdf";

            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("📅 Daily Planner - " + LocalDate.now())
                    .setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));

            for (String item : scheduleList.getItems()) {
                document.add(new Paragraph(item).setFontSize(12));
            }

            document.close();
            System.out.println("Exported successfully to: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to export PDF.");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Complete");
        alert.setHeaderText(null);
        alert.setContentText("Daily Planner exported as PDF successfully!");
        alert.showAndWait();

    }

    @FXML
    private void goBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) dateLabel.getScene().getWindow();
        stage.setScene(scene);
        System.out.println("Navigating back to main menu...");
    }
}
