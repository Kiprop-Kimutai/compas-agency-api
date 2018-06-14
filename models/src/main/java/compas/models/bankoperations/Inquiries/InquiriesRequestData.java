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
    @Column(name="narration")
    public String Narration;
    @Column(name="account")
    public String Account;
    @Column(name="deviceId")
    public String  DeviceId;
    @Column(name="tellerId")
    public String   TellerId;
    @Column(name="branchId")
    public  Integer BranchId;
    @Column(name = "customerName")
    public String CustomerName;
    public String status;

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

    public Integer getBranchId() {
        return BranchId;
    }

    public void setBranchId(Integer BranchId) {
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
}
