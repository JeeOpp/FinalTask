package support.mytag.carColour;

import entity.entityEnum.CarColour;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CarColourTag extends TagSupport implements ICarColour {
    private final static Logger log = Logger.getLogger(CarColourTag.class.getClass());
    private String colour;
    private String locale;
    private String bodyText;

    public void setColour(String colour){
        this.colour = colour;
    }
    public void setLocale(String locale){
        this.locale = locale;
    }
    private Locale getLocale(){
        return new Locale(locale);
    }

    @Override
    public int doStartTag() throws JspException {
        try{
            chooseClass();
            pageContext.getOut().write(OPEN_TAG+bodyText+CLOSE_TAG);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }
    private void chooseClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE,getLocale());
        CarColour carColourEnum = CarColour.getConstant(colour);
        switch (carColourEnum){
            case RED:
                bodyText = resourceBundle.getString(RED);
                break;
            case ORANGE:
                bodyText = resourceBundle.getString(ORANGE);
                break;
            case YELLOW:
                bodyText = resourceBundle.getString(YELLOW);
                break;
            case GREEN:
                bodyText = resourceBundle.getString(GREEN);
                break;
            case SKY:
                bodyText = resourceBundle.getString(SKY);
                break;
            case BLUE:
                bodyText = resourceBundle.getString(BLUE);
                break;
            case PURPLE:
                bodyText = resourceBundle.getString(PURPLE);
                break;
            case GREY:
                bodyText = resourceBundle.getString(GREY);
            case BLACK:
                bodyText = resourceBundle.getString(BLACK);
                break;
            case WHITE:
                bodyText = resourceBundle.getString(WHITE);
                break;
            default: bodyText = colour;
        }
    }
}
