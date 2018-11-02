package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class NwscValidateRequestData {
    private String RequestId;
    private String PhoneNo;
    private String CustRef;
    private String AreaId;
    //default constructor
    public NwscValidateRequestData(){}
    //getters and setters here

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

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }
}
