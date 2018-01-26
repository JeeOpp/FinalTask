package entity.entityEnum;

public enum CarEnum {
    NUMBER("number"),
    NAME("name"),
    COLOUR("colour");

    private String value;

    CarEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public enum CarColour {
        RED("red"),
        ORANGE("orange"),
        YELLOW("yellow"),
        GREEN("green"),
        SKY("sky"),
        BLUE("blue"),
        PURPLE("purple"),
        BLACK("black"),
        WHITE("white"),
        NONE("none");

        private String value;

        CarColour(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static CarColour getConstant(String orderStatus) {
            for (CarColour each : CarColour.values()) {
                if (each.getValue().equals(orderStatus)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}