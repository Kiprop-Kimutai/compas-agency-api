package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemePaymentData {
    private String RequestId;
    private String CustRef;
    private String PhoneNo;
    private String BranchId;
    private String Amount;
    private String TransId;
    private String TransrefNo;

    //default constructor
    public UmemePaymentData(){

    }

    //getters and setters

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getCustRef() {
        return CustRef;
    }

    public void setCustRef(String custRef) {
        CustRef = custRef;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getTransrefNo() {
        return TransrefNo;
    }

    public void setTransrefNo(String transrefNo) {
        TransrefNo = transrefNo;
    }
}
