package compas.models.bankoperations.Inquiries;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
@Entity
public class InquiriesRequestData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer Id;
    @Column(name= "transId")
    public String transId;
    @Column(name = "cbs_trans_id")
    public String cbs_trans_id;
    public String terminal_trans_id;
    @Column(name="narration")
    public String Narration;
    @Column(name="account")
    public String Account;
    @Column(name="deviceId")
    public String  DeviceId;
    @Column(name="tellerId")
    public String   TellerId;
    @Column(name="branchId")
    //public  Integer BranchId;
    public String BranchId;
    @Column(name = "customerName")
    public String CustomerName;
    public String status;
    public String [] Charges;
    public String finnacle_response_message;
    public String [] AccountsList;
    @Column(name = "from_date")
    public String From;
    @Column(name = "to_date")
    public String To;
    public String SchemeCode;
    //default constructor
    public InquiriesRequestData(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getCbs_trans_id() {
        return cbs_trans_id;
    }

    public void setCbs_trans_id(String cbs_trans_id) {
        this.cbs_trans_id = cbs_trans_id;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String Narration) {
        this.Narration = Narration;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String[] getAccountsList() {
        return AccountsList;
    }

    public void setAccountsList(String[] accountsList) {
        AccountsList = accountsList;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

    public String getTellerId() {
        return TellerId;
    }

    public void setTellerId(String TellerId) {
        this.TellerId = TellerId;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String BranchId) {
        this.BranchId = BranchId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getString(){
        return String.format("inquiriesRequest[transid = %s  account = %s narration = %s  deviceid = %s branchid = %s  tellerid = %s]",transId,Account,Narration,DeviceId,BranchId,TellerId);
    }

    public String[] getCharges() {
        return Charges;
    }

    public String getFinnacle_response_message() {
        return finnacle_response_message;
    }

    public void setFinnacle_response_message(String finnacle_response_message) {
        this.finnacle_response_message = finnacle_response_message;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public void setCharges(String[] charges) {
        Charges = charges;
    }

    public String getTerminal_trans_id() {
        return terminal_trans_id;
    }

    public void setTerminal_trans_id(String terminal_trans_id) {
        this.terminal_trans_id = terminal_trans_id;
    }
}
