package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 8/17/2018.
 */
public class BatchBalances {
    private String Account;
    private String Balance;
    private String ActiveStatus;
    private String ClosingStatus;

    //default constructor
    public BatchBalances(){

    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getClosingStatus() {
        return ClosingStatus;
    }

    public void setClosingStatus(String closingStatus) {
        ClosingStatus = closingStatus;
    }
}
