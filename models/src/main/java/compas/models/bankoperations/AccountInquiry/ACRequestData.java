package compas.models.bankoperations.AccountInquiry;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class ACRequestData {
    private String Account;
    private String RequestId;
    private String DeviceId;
    private String TellerId;
    private String BranchId;
    public ACRequestData(){}

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
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
}
