package support.mytag.orderStatus;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class OrderStatusTagWithBody extends BodyTagSupport {
    private final static Logger log = Logger.getLogger(OrderStatusTagWithBody.class.getClass());
    private static final String primary = "colour-accepted";
    private static final String warning = "colour-processed";
    private static final String success = "colour-completed";
    private static final String danger = "colour-rejected";
    private static final String info = "colour-archive";
    private String orderStatus;
    private String locale;
    private String bodyText;


    public void setOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
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
            String attrName = chooseClass();
            pageContext.getOut().write("<td class="+ attrName + ">");
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            pageContext.getOut().write(bodyText);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try{
            pageContext.getOut().write("</td>");
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }
    private String chooseClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localization.local",getLocale());
        OrderStatus orderEnum = OrderStatus.getConstant(orderStatus);
        switch (orderEnum){
            case PROCESSED:
                bodyText = resourceBundle.getString("local.statusOrder.processed");
                return warning;
            case REJECTED:
                bodyText = resourceBundle.getString("local.statusOrder.rejected");
                return danger;
            case ACCEPTED:
                bodyText = resourceBundle.getString("local.statusOrder.accepted");
                return primary;
            case COMPLETED:
                bodyText = resourceBundle.getString("local.statusOrder.completed");
                return success;
            case ARCHIVE:
                bodyText = resourceBundle.getString("local.statusOrder.archive");
                return info;
        }
        return null;
    }
}
