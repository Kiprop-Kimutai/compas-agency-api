package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscAreasRequestData {
    private String RequestId;
    private String PhoneNo;

    //default constructor
    public NwscAreasRequestData(){}
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
}
