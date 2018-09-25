package compas.models.utilities;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UtilityRequest {
    private String utility;
    private String menu;
    private UtilityRequestData transactionbody;

    //default constructor
    public UtilityRequest(){}

    //getters and setters

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public UtilityRequestData getTransactionbody() {
        return transactionbody;
    }

    public void setTransactionbody(UtilityRequestData transactionbody) {
        this.transactionbody = transactionbody;
    }
}
