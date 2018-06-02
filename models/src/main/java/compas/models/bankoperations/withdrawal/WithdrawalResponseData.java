package compas.models.bankoperations.withdrawal;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class WithdrawalResponseData {
    private String TransId;
    private String Account;
    private String Amount;

    //default constructor
    public WithdrawalResponseData(){}

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
