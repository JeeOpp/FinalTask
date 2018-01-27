package entity.entityEnum;

public enum UserEnum {
    USER("user"),
    CLIENT("client"),
    TAXI("taxi"),
    ADMIN("admin"),
    TAXI_ID("taxiId"),
    CLIENT_ID("clientId"),
    ID("id"),
    LOGIN("login"),
    PASSWORD("password"),
    NEW_PASS("newPass"),
    PREVIOUS_PASS("previousPass"),
    FIRST_NAME("firstName"),
    NAME("name"),
    LAST_NAME("lastName"),
    SURNAME("surname"),
    BAN_STATUS("banStatus"),
    ROLE("role"),
    BONUS_POINTS("bonusPoints"),
    EMAIL("email"),
    AVAILABLE_STATUS("availableStatus"),
    CHANGE_TAXI_ID("changeTaxiId"),
    CAR("car");

    private String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}