package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class InquiriesResponseData {
    private String TransId;
    private String Balance;
    private String Account;
    private String [] Transactions;

    //default constructor
    public InquiriesResponseData(){}

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String[] getTransactions() {
        return Transactions;
    }

    public void setTransactions(String[] transactions) {
        Transactions = transactions;
    }

    public String getString(){
        return String.format("responseData[TransId = %s  Balance = %s  Account = %s ]",TransId,Balance,Account);
    }
}
