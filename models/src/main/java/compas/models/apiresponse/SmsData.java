package compas.models.apiresponse;

/**
 * Created by CLLSDJACKT013 on 7/3/2018.
 */
public class SmsData {
    public String Vendor_ref;
    public String TransId;

    public SmsData(){

    }

    public String getVendor_ref() {
        return Vendor_ref;
    }

    public void setVendor_ref(String vendor_ref) {
        Vendor_ref = vendor_ref;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }
}
