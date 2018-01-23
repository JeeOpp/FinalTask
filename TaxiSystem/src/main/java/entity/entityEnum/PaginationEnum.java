package entity.entityEnum;

public enum PaginationEnum {
    PAGE("page"),
    RECORDS("records"),
    NUM_PAGE("numPage"),
    PAGE_COUNT("countPages"),
    PAGE_CLIENT_LIST("pageClientList"),
    PAGE_TAXI_LIST("pageTaxiList"),
    PAGE_ORDER_LIST("pageOrderList"),
    CURRENT_PAGE("currentPage");

    private String value;

    PaginationEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
