package support.mytag.carColour;

/**
 * Created by DNAPC on 21.01.2018.
 */
public enum Colour {
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

    Colour(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

    public static Colour getConstant(String orderStatus){
        for (Colour each: Colour.values()){
            if(each.getValue().equals(orderStatus)){
                return each;
            }
        }
        return NONE;
    }
}
