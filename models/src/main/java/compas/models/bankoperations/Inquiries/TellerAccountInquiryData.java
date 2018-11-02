package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 10/22/2018.
 */
public class TellerAccountInquiryData {
    private String RequestId;
    private String TellerId;
    private String BranchId;

    //default constructor
    public TellerAccountInquiryData(){}
    //generate getters and setters here

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
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
