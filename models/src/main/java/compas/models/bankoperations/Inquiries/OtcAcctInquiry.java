package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 8/23/2018.
 */
public class OtcAcctInquiry {

    private String account_number;
    private String action; //acct_inquiry

    //default constructor
    public  OtcAcctInquiry(){}

    //generate getter and setter


    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
