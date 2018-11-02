package compas.models.utilities.ura;

import compas.models.utilities.Extras;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PaymentWithPRNRequestData {
    private String RequestId;
    private String CustRef;
    private String PhoneNo;
    private String BranchId;
    private String Amount;
    private String TransId;
    //private URAExtras Extras;
    private compas.models.utilities.Extras Extras;
    //default constructor
    public PaymentWithPRNRequestData(){}
    //getters and setters here

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

    public Extras getExtras() {
        return Extras;
    }

    public void setExtras(Extras extras) {
        Extras = extras;
    }
}
