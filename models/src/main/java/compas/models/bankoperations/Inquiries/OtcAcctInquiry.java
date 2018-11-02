package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 8/23/2018.
 */
public class OtcAcctInquiry {
<<<<<<< HEAD
    private String account_number;
    private String action; //acct_inquiry
=======
    public String account_number;
    public String teller_Id;
>>>>>>> e935b1541eed472e988ae9bec1577ed567636090

    //default constructor
    public  OtcAcctInquiry(){}

    //generate getter and setter


    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

<<<<<<< HEAD
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
=======
    public String getTeller_Id() {
        return teller_Id;
    }

    public void setTeller_Id(String account_number) {
        this.teller_Id = teller_Id;
>>>>>>> e935b1541eed472e988ae9bec1577ed567636090
    }
}
