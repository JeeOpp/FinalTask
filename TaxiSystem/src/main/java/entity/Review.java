package entity;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bean, contains information about reviews.
 */
public class Review {
    private static final Logger log = Logger.getLogger(Review.class);
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

    public void setFromResultSet(ResultSet resultSet) {
        try {
            this.setReviewId(resultSet.getInt(1));
            this.setClient((Client) new Client.ClientBuilder().setId(resultSet.getInt(2)).build());
            this.setTaxi((Taxi) new Taxi.TaxiBuilder().setId(resultSet.getInt(3)).build());
            this.setComment(resultSet.getString(4));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}