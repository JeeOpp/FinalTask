package entity;

/**
 * Bean, contains information about reviews.
 */
public class Review {
    private int reviewId;
    private Client client;
    private Taxi taxi;
    private String comment;

    public Review() {
    }

    public Review(Client client, Taxi taxi, String comment) {
        this.client = client;
        this.taxi = taxi;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public Client getClient() {
        return client;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public String getComment() {
        return comment;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}