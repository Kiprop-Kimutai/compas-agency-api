package compas.models.bankoperations.reversals;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class ReversalResponseData {
    private String TransId;
    private String OldTransId;
    private String OldVendor_ref;
    private String Amount;

    //default constructor
    public ReversalResponseData(){

    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getOldTransId() {
        return OldTransId;
    }

    public void setOldTransId(String oldTransId) {
        OldTransId = oldTransId;
    }

    public String getOldVendor_ref() {
        return OldVendor_ref;
    }

    public void setOldVendor_ref(String oldVendor_ref) {
        OldVendor_ref = oldVendor_ref;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
