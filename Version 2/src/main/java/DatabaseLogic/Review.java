package DatabaseLogic;

public class Review {
    private int reviewID;
    private int eventID;
    private String source;
    private String content;
    private int rating;

    public Review(int reviewID, int eventID, String source, String content, int rating) {
        this.reviewID = reviewID;
        this.eventID = eventID;
        this.source = source;
        this.content = content;
        this.rating = rating;
    }
    public int getReviewID() { return reviewID; }
    public int getEventID() { return eventID; }
    public String getSource() { return source; }
    public String getContent() { return content; }
    public int getRating() { return rating; }
    public void setReviewID(int reviewID) { this.reviewID = reviewID; }
    public void setEventID(int eventID) { this.eventID = eventID; }
    public void setSource(String source) { this.source = source; }
    public void setContent(String content) { this.content = content; }
    public void setRating(int rating) { this.rating = rating; }
}
