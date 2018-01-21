package support.mytag.carColour;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 21.01.2018.
 */
public class CarColourTag extends TagSupport {
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
            pageContext.getOut().write("<td>"+bodyText+"</td>");
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }
    private void chooseClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localization.local",getLocale());
        Colour colourEnum = Colour.getConstant(colour);
        switch (colourEnum){
            case RED:
                bodyText = resourceBundle.getString("local.colour.red");
                break;
            case ORANGE:
                bodyText = resourceBundle.getString("local.colour.orange");
                break;
            case YELLOW:
                bodyText = resourceBundle.getString("local.colour.yellow");
                break;
            case GREEN:
                bodyText = resourceBundle.getString("local.colour.green");
                break;
            case SKY:
                bodyText = resourceBundle.getString("local.colour.sky");
                break;
            case BLUE:
                bodyText = resourceBundle.getString("local.colour.blue");
                break;
            case PURPLE:
                bodyText = resourceBundle.getString("local.colour.purple");
                break;
            case BLACK:
                bodyText = resourceBundle.getString("local.colour.black");
                break;
            case WHITE:
                bodyText = resourceBundle.getString("local.colour.white");
                break;
        }
    }
}
