package controllers;

import DatabaseLogic.Review;
import DatabaseLogic.DatabaseConnection;
import DatabaseLogic.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;

import java.util.Comparator;
import java.util.HashMap; // Add this import
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReviewsController {
    public Button backButton;
    @FXML private TabPane eventTabs;
    @FXML private ToggleButton sortByRatingButton;
    @FXML private ComboBox<String> eventComboBox;
    @FXML private TextField nameField;
    @FXML private TextField reviewField;
    @FXML private TextField ratingField;

    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    private ObservableList<Review> reviewDataList = FXCollections.observableArrayList();
    // Initialize the maps to avoid NullPointerException
    private Map<Integer, ListView<String>> eventReviewLists = new HashMap<>();
    private Map<Integer, ObservableList<Review>> eventReviewDataLists = new HashMap<>();
    private boolean sortHighToLow = true; // Default sort direction

    @FXML
    public void initialize() {
        // Load events into the ComboBox
        loadEvents();
        eventComboBox.setItems(eventList.stream()
                .map(Event::getName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        // Fetch reviews and populate tabs
        loadReviewsIntoTabs();
    }

    private void loadEvents() {
        List<Event> events = DatabaseConnection.getAllEvents();
        eventList.clear();
        eventList.addAll(events);
        System.out.println("Loaded " + events.size() + " events into ComboBox.");
    }

    private void loadReviewsIntoTabs() {
        // Fetch all reviews
        List<Review> reviews = DatabaseConnection.getAllReviews();
        System.out.println("Fetched " + reviews.size() + " reviews from the database.");
        reviews.forEach(review -> System.out.println("Review: " + review.getSource() + ": " + review.getContent() + " (Rating: " + review.getRating() + ")"));

        reviewDataList.clear();
        reviewDataList.addAll(reviews);

        // Group reviews by event_id
        Map<Integer, List<Review>> reviewsByEvent = reviews.stream()
                .collect(Collectors.groupingBy(Review::getEventID));

        // Clear existing tabs
        eventTabs.getTabs().clear();

        // Create a tab for each event
        for (Event event : eventList) {
            Tab tab = new Tab(event.getName());
            tab.setClosable(false); // Prevent closing the tab

            // Create a ListView for this event's reviews
            ListView<String> reviewList = new ListView<>();
            ObservableList<String> reviewDisplayList = FXCollections.observableArrayList();
            ObservableList<Review> eventReviewsList = FXCollections.observableArrayList();

            // Add reviews for this event
            List<Review> eventReviews = reviewsByEvent.getOrDefault(event.getEventId(), List.of());
            eventReviewsList.addAll(eventReviews);
            updateReviewDisplayList(eventReviewsList, reviewDisplayList);

            reviewList.setItems(reviewDisplayList);

            // Set a placeholder if there are no reviews
            reviewList.setPlaceholder(new Label("No reviews available for this event"));

            // Add the ListView to the tab
            tab.setContent(reviewList);

            // Add the tab to the TabPane
            eventTabs.getTabs().add(tab);

            // Store the ListView and reviews for sorting later
            eventReviewLists.put(event.getEventId(), reviewList);
            eventReviewDataLists.put(event.getEventId(), eventReviewsList);
        }

        // If there are no events, add a default tab
        if (eventList.isEmpty()) {
            Tab noEventsTab = new Tab("No Events Available");
            noEventsTab.setClosable(false);
            eventTabs.getTabs().add(noEventsTab);
        }
    }

    private void updateReviewDisplayList(ObservableList<Review> reviews, ObservableList<String> displayList) {
        displayList.clear();
        List<Review> sortedReviews = reviews.stream()
                .sorted(sortHighToLow ?
                        Comparator.comparingInt(Review::getRating).reversed() :
                        Comparator.comparingInt(Review::getRating))
                .collect(Collectors.toList());
        displayList.addAll(sortedReviews.stream()
                .map(review -> review.getSource() + ": " + review.getContent() + " (Rating: " + review.getRating() + ")")
                .collect(Collectors.toList()));
    }

    @FXML
    private void handleSortByRating() {
        sortHighToLow = !sortHighToLow;
        sortByRatingButton.setText(sortHighToLow ? "Sort by Rating (High to Low)" : "Sort by Rating (Low to High)");

        // Update all tabs with the new sorting
        for (Event event : eventList) {
            ObservableList<Review> reviews = eventReviewDataLists.get(event.getEventId());
            ListView<String> reviewList = eventReviewLists.get(event.getEventId());
            if (reviews != null && reviewList != null) {
                updateReviewDisplayList(reviews, reviewList.getItems());
            }
        }
    }

    @FXML
    private void handleFetchReviews() {
        // Do nothing (as requested)
    }

    @FXML
    private void handleForwardReview() {
        // Get the currently selected tab
        Tab selectedTab = eventTabs.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            showAlert("No Event Selected", "Please select an event tab.");
            return;
        }

        // Find the ListView in the selected tab
        ListView<String> reviewList = (ListView<String>) selectedTab.getContent();
        String selectedReview = reviewList.getSelectionModel().getSelectedItem();

        if (selectedReview != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Forward Review");
            alert.setHeaderText(null);
            alert.setContentText("Review forwarded to client:\n\n" + selectedReview);
            alert.showAndWait();
        } else {
            showAlert("No Review Selected", "Please select a review to forward.");
        }
    }

    @FXML
    private void handleAddReview() {
        String name = nameField.getText().trim();
        String reviewContent = reviewField.getText().trim();
        String ratingText = ratingField.getText().trim();
        String selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();

        // Validate inputs
        if (reviewContent.isEmpty()) {
            showAlert("Input Error", "Review content cannot be empty.");
            return;
        }
        if (selectedEvent == null) {
            showAlert("Input Error", "Please select an event.");
            return;
        }
        int rating;
        try {
            rating = Integer.parseInt(ratingText);
            if (rating < 0 || rating > 5) {
                showAlert("Input Error", "Rating must be between 0 and 5.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Rating must be a number between 0 and 5.");
            return;
        }

        // Find the selected event's ID
        Event selectedEventObj = eventList.stream()
                .filter(event -> event.getName().equals(selectedEvent))
                .findFirst()
                .orElse(null);
        if (selectedEventObj == null) {
            showAlert("Error", "Selected event not found.");
            return;
        }

        // Create a new Review object
        Review review = new Review(
                0, // review_id (will be set by the database)
                selectedEventObj.getEventId(),
                name.isEmpty() ? "Anonymous" : name,
                reviewContent,
                rating
        );

        System.out.println("Adding review: " + review.getSource() + ": " + review.getContent() + " (Event ID: " + review.getEventID() + ", Rating: " + review.getRating() + ")");

        // Add to the database
        DatabaseConnection.addReview(review);

        // Refresh the tabs
        loadReviewsIntoTabs();

        // Clear the input fields
        eventComboBox.getSelectionModel().clearSelection();
        nameField.clear();
        reviewField.clear();
        ratingField.clear();
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) eventTabs.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleDeleteReview() {
        // Get the currently selected tab
        Tab selectedTab = eventTabs.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            showAlert("No Event Selected", "Please select an event tab.");
            return;
        }

        // Find the ListView in the selected tab
        ListView<String> reviewList = (ListView<String>) selectedTab.getContent();
        String selectedReview = reviewList.getSelectionModel().getSelectedItem();
        int selectedIndex = reviewList.getSelectionModel().getSelectedIndex();

        if (selectedReview != null && selectedIndex >= 0) {
            // Find the corresponding event
            String eventName = selectedTab.getText();
            Event event = eventList.stream()
                    .filter(e -> e.getName().equals(eventName))
                    .findFirst()
                    .orElse(null);
            if (event == null) {
                showAlert("Error", "Selected event not found.");
                return;
            }

            // Filter reviews for this event
            List<Review> eventReviews = reviewDataList.stream()
                    .filter(review -> review.getEventID() == event.getEventId())
                    .collect(Collectors.toList());

            // Get the review to delete
            Review reviewToDelete = eventReviews.get(selectedIndex);

            // Delete from the database
            DatabaseConnection.deleteReview(reviewToDelete.getReviewID());

            // Refresh the tabs
            loadReviewsIntoTabs();
        } else {
            System.out.println("No review selected to delete.");
            showAlert("No Review Selected", "Please select a review to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}