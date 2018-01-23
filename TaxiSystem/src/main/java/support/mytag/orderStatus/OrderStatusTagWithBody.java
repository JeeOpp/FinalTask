package support.mytag.orderStatus;

import entity.entityEnum.OrderEnum;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderStatusTagWithBody extends BodyTagSupport implements IOrderStatus {
    private final static Logger log = Logger.getLogger(OrderStatusTagWithBody.class.getClass());
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
            pageContext.getOut().write(START_OPEN_TAG+ attrName + END_OPEN_TAG);
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
            pageContext.getOut().write(CLOSE_TAG);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }
    private String chooseClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE,getLocale());
        OrderEnum.OrderStatus orderEnum = OrderEnum.OrderStatus.getConstant(orderStatus);
        switch (orderEnum){
            case PROCESSED:
                bodyText = resourceBundle.getString(LOCALE_PROCESSED);
                return WARNING;
            case REJECTED:
                bodyText = resourceBundle.getString(LOCALE_REJECTED);
                return DANGER;
            case ACCEPTED:
                bodyText = resourceBundle.getString(LOCALE_ACCEPTED);
                return PRIMARY;
            case COMPLETED:
                bodyText = resourceBundle.getString(LOCALE_COMPLETED);
                return SUCCESS;
            case ARCHIVE:
                bodyText = resourceBundle.getString(LOCALE_ARCHIVE);
                return INFO;
        }
        return null;
    }
}
