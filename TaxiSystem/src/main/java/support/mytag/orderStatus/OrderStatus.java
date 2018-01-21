package support.mytag.orderStatus;

/**
 * Created by DNAPC on 21.01.2018.
 */
public enum OrderStatus {
    COMPLETED("completed"),
    REJECTED("rejected"),
    ACCEPTED("accepted"),
    PROCESSED("processed"),
    ARCHIVE("archive"),
    NONE("none");

    private String value;

    OrderStatus(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

    public static OrderStatus getConstant(String orderStatus){
        for (OrderStatus each: OrderStatus.values()){
            if(each.getValue().equals(orderStatus)){
                return each;
            }
        }
        return NONE;
    }
}
