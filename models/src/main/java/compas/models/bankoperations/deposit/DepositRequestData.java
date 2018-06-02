package compas.models.bankoperations.deposit;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class DepositRequestData {
    private String TransId;
    private String Narration;
    private String Account;
    private String Amount;
    private String DeviceId;
    private String TellerId;
    private String BranchId;
    private String CustomerName;

    //default constructor
    public DepositRequestData(){}

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
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

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getTellerId() {
        return TellerId;
    }

    public void setTellerId(String tellerId) {
        TellerId = tellerId;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}