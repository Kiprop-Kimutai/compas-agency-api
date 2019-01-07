package compas.models.utilities.ura;

import compas.models.utilities.Extras;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class ValidateWithPRNRequestData {
    private String RequestId;
    private String PhoneNo;
    private String CustRef;
    //private URAExtras Extras;
    private Extras Extras;

    //default constructor
    public ValidateWithPRNRequestData(){}

    //getters and setters

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getCustRef() {
        return CustRef;
    }

    public void setCustRef(String custRef) {
        CustRef = custRef;
    }

    public Extras getExtras() {
        return Extras;
    }

    public void setExtras(Extras extras) {
        Extras = extras;
    }
}