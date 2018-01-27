package entity.entityEnum;

public enum ReviewEnum {
    REVIEW("review"),
    REVIEW_ID("reviewId"),
    COMMENT("comment"),
    USER_REVIEWS("userReviews");

    private String value;

    ReviewEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}