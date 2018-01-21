package controller.command.impl;

/**
 * Created by DNAPC on 12.01.2018.
 */
public enum SwitchConstant {
    PRE_ORDER("preOrder"),
    CALL_TAXI("callTaxi"),
    GET_CLIENT_ORDERS("getClientOrders"),
    GET_TAXI_ORDERS("getTaxiOrders"),
    CANCEL_ORDER("cancelOrder"),
    ACCEPT_ORDER("acceptOrder"),
    REJECT_ORDER("rejectOrder"),
    PAY_ORDER("payOrder"),
    GET_ALL_ORDERS("getAllOrders"),
    DELETE_ALL_ORDERS("deleteAllOrders"),
    GET_JSON_CAR_LIST("getJsonCarList"),

    AUTHORIZATION("authorization"),
    REGISTRATION("registration"),
    LOG_OUT("logOut"),
    GO_HOME_PAGE("goHomePage"),

    WRITE_REVIEW("writeReview"),

    GET_CAR_LIST("getCarList"),
    ADD_CAR("addCar"),
    REMOVE_CAR("removeCar"),

    PRE_PROFILE("preProfile"),
    CHANGE_PASSWORD("changePassword"),
    GET_CLIENT_LIST("getClientList"),
    GET_TAXI_LIST("getTaxiList"),
    CHANGE_BAN_STATUS("changeBanStatus"),
    CHANGE_BONUS_COUNT("changeBonusCount"),
    CHANGE_TAXI_CAR("changeTaxiCar"),

    SEND_QUESTION("sendQuestion"),
    PRE_RESTORE("preRestore"),

    NONE("none");
    private String value;

    SwitchConstant(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

    public static SwitchConstant getConstant(String action){
        for (SwitchConstant each: SwitchConstant.values()){
            if(each.getValue().equals(action)){
                return each;
            }
        }
        return NONE;
    }
}
