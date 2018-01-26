package entity.entityEnum;

public enum OrderEnum {
    ORDER_ID("orderId"),
    CLIENT_ORDER("clientOrder"),
    TAXI_ORDER("taxiOrder"),
    ORDER_STATUS("orderStatus"),
    SOURCE_COORD("sourceCoordinate"),
    DESTINY_COORD("destinyCoordinate"),
    BONUS("bonus"),
    PRICE("price"),
    CHECKED_CAR("checkedCarNumber");

    private String value;

    OrderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public enum OrderAction {
        ACCEPT("accept"),
        REJECT("reject"),
        CANCEL("cancel"),
        PAY("pay"),
        ARCHIVE("archive");

        private String value;

        OrderAction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderStatus {
        COMPLETED("completed"),
        REJECTED("rejected"),
        ACCEPTED("accepted"),
        PROCESSED("processed"),
        ARCHIVE("archive"),
        NONE("none");

        private String value;

        OrderStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static OrderStatus getConstant(String orderStatus) {
            for (OrderStatus each : OrderStatus.values()) {
                if (each.getValue().equals(orderStatus)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}