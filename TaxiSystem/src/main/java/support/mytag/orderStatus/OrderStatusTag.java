package support.mytag.orderStatus;

import entity.entityEnum.OrderEnum;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 02.01.2018.
 */
public class OrderStatusTag extends TagSupport implements IOrderStatus {
    private final static Logger log = Logger.getLogger(OrderStatusTag.class.getClass());
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
            pageContext.getOut().write(START_OPEN_TAG+ attrName + END_OPEN_TAG + bodyText + CLOSE_TAG);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
        return SKIP_BODY;
    }
    private String chooseClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE,getLocale());
        OrderEnum.OrderStatus orderStatusEnum = OrderEnum.OrderStatus.getConstant(orderStatus);
        switch (orderStatusEnum){
            case PROCESSED:
                bodyText = resourceBundle.getString(LOCALE_PROCESSED);
                return WARNING;
            case ACCEPTED:
                bodyText = resourceBundle.getString(LOCALE_ACCEPTED);
                return PRIMARY;
            case REJECTED:
                bodyText = resourceBundle.getString(LOCALE_REJECTED);
                return DANGER;
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
