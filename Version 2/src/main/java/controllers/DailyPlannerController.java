package controllers;

import DatabaseLogic.DatabaseConnection;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyPlannerController {

    @FXML private Label dateLabel;
    @FXML private TableView<DailyScheduleEntry> dailyScheduleTable;
    @FXML private TableColumn<DailyScheduleEntry, String> roomColumn;
    @FXML private TableColumn<DailyScheduleEntry, String> timeColumn;
    @FXML private TableColumn<DailyScheduleEntry, String> activityColumn;
    @FXML private TableColumn<DailyScheduleEntry, String> bookedByColumn;
    @FXML private TableColumn<DailyScheduleEntry, String> configurationColumn;
    @FXML private TableColumn<DailyScheduleEntry, String> notesColumn;
    @FXML private ListView<String> scheduleList;

    private ObservableList<DailyScheduleEntry> scheduleEntries;

    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        dateLabel.setText("Today is " + today.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));

        // Initialize table columns
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().roomProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        activityColumn.setCellValueFactory(cellData -> cellData.getValue().activityProperty());
        bookedByColumn.setCellValueFactory(cellData -> cellData.getValue().bookedByProperty());
        configurationColumn.setCellValueFactory(cellData -> cellData.getValue().configurationProperty());
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

        // Load today's schedule
        loadTodaySchedule();
    }

    private void loadTodaySchedule() {
        scheduleEntries = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        String query = "SELECT b.room_id, b.start_time, b.end_time, b.configuration_details, b.status, " +
                "r.name AS room_name, c.name AS client_name, e.name AS event_name " +
                "FROM Bookings b " +
                "JOIN Rooms r ON b.room_id = r.room_id " +
                "JOIN Clients c ON b.client_id = c.client_id " +
                "LEFT JOIN Event_details e ON b.event_id = e.event_id " +
                "WHERE b.date = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, today.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String roomName = rs.getString("room_name");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String time = startTime + " - " + endTime;
                String eventName = rs.getString("event_name");
                String clientName = rs.getString("client_name");
                String configuration = rs.getString("configuration_details");
                String status = rs.getString("status");

                // Use event name if available, otherwise indicate it's a setup/booking
                String activity = (eventName != null && !eventName.isEmpty()) ? eventName : "Setup/Booking";
                String notes = "Status: " + status;

                DailyScheduleEntry entry = new DailyScheduleEntry(
                        roomName,
                        time,
                        activity,
                        clientName,
                        configuration != null ? configuration : "N/A",
                        notes
                );
                scheduleEntries.add(entry);

                // Add to ListView for compatibility
                String listEntry = String.format("%s | %s | %s | Booked by: %s | Config: %s | %s",
                        roomName, time, activity, clientName, configuration != null ? configuration : "N/A", notes);
                scheduleList.getItems().add(listEntry);
            }

            dailyScheduleTable.setItems(scheduleEntries);
        } catch (SQLException e) {
            System.out.println("Error loading today's schedule: " + e.getMessage());
        }
    }

    @FXML
    private void exportAsPDF() {
        try {
            String userHome = System.getProperty("user.home");
            String path = userHome + "/Downloads/DailyPlanner_" + LocalDate.now() + ".pdf";

            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            document.add(new Paragraph("Daily Planner - " + LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")))
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Create a table with 6 columns
            Table table = new Table(UnitValue.createPercentArray(new float[]{15, 15, 20, 15, 15, 20}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add table headers
            table.addHeaderCell(new Cell().add(new Paragraph("Room").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Time").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Activity").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Booked By").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Configuration").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Notes").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            // Add table rows
            for (DailyScheduleEntry entry : scheduleEntries) {
                table.addCell(new Cell().add(new Paragraph(entry.getRoom())));
                table.addCell(new Cell().add(new Paragraph(entry.getTime())));
                table.addCell(new Cell().add(new Paragraph(entry.getActivity())));
                table.addCell(new Cell().add(new Paragraph(entry.getBookedBy())));
                table.addCell(new Cell().add(new Paragraph(entry.getConfiguration())));
                table.addCell(new Cell().add(new Paragraph(entry.getNotes())));
            }

            document.add(table);
            document.close();
            System.out.println("Exported successfully to: " + path);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Complete");
            alert.setHeaderText(null);
            alert.setContentText("Daily Planner exported as PDF successfully!");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Failed to export PDF: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to export Daily Planner as PDF.");
            alert.showAndWait();
        }
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