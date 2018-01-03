package support.mytag;

import org.apache.taglibs.standard.tag.el.fmt.BundleTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class SelectColourTagWithBody extends BodyTagSupport {
    private static final String primary = "colour-accepted";  //accepted
    private static final String warning = "colour-processed";  //processed
    private static final String success = "colour-completed"; //completed
    private static final String danger = "colour-rejected"; //rejected
    private static final String info = "colour-archive";  //archive
    private String orderStatus;
    private String locale;
    private String bodyText;
    private ResourceBundle resourceBundle = null;

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
            ///log
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            pageContext.getOut().write(bodyText);
        }catch (IOException ex){
            //log;
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try{
            pageContext.getOut().write("</td>");
        }catch (IOException ex){
            ///log
        }
        return SKIP_BODY;
    }
    private String chooseClass(){
        resourceBundle = ResourceBundle.getBundle("localization.local",getLocale());
        if (orderStatus.equals("rejected")){
            bodyText = resourceBundle.getString("local.statusOrder.rejected");
            return danger;
        }
        if (orderStatus.equals("completed")){
            bodyText = resourceBundle.getString("local.statusOrder.completed");
            return success;
        }
        if (orderStatus.equals("accepted")){
            bodyText = resourceBundle.getString("local.statusOrder.accepted");
            return primary;
        }
        if (orderStatus.equals("processed")){
            bodyText = resourceBundle.getString("local.statusOrder.processed");
            return warning;
        }
        if (orderStatus.equals("archive")){
            bodyText = resourceBundle.getString("local.statusOrder.archive");
            return info;
        }
        return null;
    }
}
