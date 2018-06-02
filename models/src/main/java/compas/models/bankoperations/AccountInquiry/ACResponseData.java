package compas.models.bankoperations.AccountInquiry;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class ACResponseData {
    private String TransId;
    private String Account;
    private String Amount;
    private String AcctName;
    private String ImageData;
    private String Schemecode;
    private String ActiveStatus;
    private String SolID;
    private String PBUNo;

    public ACResponseData(){}

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAcctName() {
        return AcctName;
    }

    public void setAcctName(String acctName) {
        AcctName = acctName;
    }

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }

    public String getSchemecode() {
        return Schemecode;
    }

    public void setSchemecode(String schemecode) {
        Schemecode = schemecode;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getSolID() {
        return SolID;
    }

    public void setSolID(String solID) {
        SolID = solID;
    }

    public String getPBUNo() {
        return PBUNo;
    }

    public void setPBUNo(String PBUNo) {
        this.PBUNo = PBUNo;
    }
}
