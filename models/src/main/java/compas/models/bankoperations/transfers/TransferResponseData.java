package compas.models.bankoperations.transfers;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class TransferResponseData {
    private String FromAccount;
    private String ToAccount;
    private String Amount;

    //default constructor
    public TransferResponseData(){}

    public String getFromAccount() {
        return FromAccount;
    }

    public void setFromAccount(String fromAccount) {
        FromAccount = fromAccount;
    }

    public String getToAccount() {
        return ToAccount;
    }

    public void setToAccount(String toAccount) {
        ToAccount = toAccount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
