package compas.models.bankoperations.AccountInquiry;

/**
 * Created by CLLSDJACKT013 on 8/6/2018.
 */
public class AcctInquiryResponseData {
    public String TransId;
    public String Account;
    public String phone;
    public String Amount;
    public String AcctName;
    public String Email;
    public String ImageData;
    public String Schemecode;
    public String ActiveStatus;
    public String SolID;
    public String PBUNo;
    //public String phone;

    //default constructor

    public AcctInquiryResponseData() {
    }

    //getters and setters here

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}