package compas.models.bankoperations.transfers;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class TransferRequestData {
    private String TransId;
    private String Narration;
    private String DeviceId;
    private String TellerId;
    private String BranchId;
    private String CustomerName;
    private String FromAccount;
    private String ToAccount;
    private String ReferenceAccount;
    private String Amount;
    private String [] Charges;

    //default constructror
    public TransferRequestData(){}

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

    public String getReferenceAccount() {
        return ReferenceAccount;
    }

    public void setReferenceAccount(String referenceAccount) {
        ReferenceAccount = referenceAccount;
    }

    public String[] getCharges() {
        return Charges;
    }

    public void setCharges(String[] charges) {
        Charges = charges;
    }
}
