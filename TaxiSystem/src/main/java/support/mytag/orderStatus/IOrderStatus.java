package support.mytag.orderStatus;

/**
 * Created by DNAPC on 23.01.2018.
 */
public interface IOrderStatus {
    String PRIMARY = "colour-accepted";
    String WARNING = "colour-processed";
    String SUCCESS = "colour-completed";
    String DANGER = "colour-rejected";
    String INFO = "colour-archive";

    String START_OPEN_TAG = "<td class=";
    String END_OPEN_TAG = ">";
    String CLOSE_TAG = "</td>";

    String BUNDLE = "localization.local";
    String LOCALE_PROCESSED = "local.statusOrder.processed";
    String LOCALE_ACCEPTED = "local.statusOrder.accepted";
    String LOCALE_REJECTED = "local.statusOrder.rejected";
    String LOCALE_COMPLETED = "local.statusOrder.completed";
    String LOCALE_ARCHIVE = "local.statusOrder.archive";

}
