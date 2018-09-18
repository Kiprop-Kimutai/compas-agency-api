package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 8/7/2018.
 */
public class BatchAccountBalanceInquiryData {
    private String RequestId;
    private String DeviceId;
    private String TellerId;
    private String BranchId;
    private String [] AccountsList;

    //default constructor
    public BatchAccountBalanceInquiryData() {
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

    public String[] getAccountsList() {
        return AccountsList;
    }

    public void setAccountsList(String[] accountsList) {
        AccountsList = accountsList;
    }
}
