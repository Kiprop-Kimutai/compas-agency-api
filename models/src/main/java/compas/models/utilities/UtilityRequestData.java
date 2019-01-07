package compas.models.utilities;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
@Entity
public class UtilityRequestData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String RequestId;
    private String CustRef;
    private String PhoneNo;
    private String BranchId;
    private String Amount;
    private String TransId;
    private String TransrefNo;
    private String AreaId;
    @Transient
    private Extras Extras;
    //private compas.models.utilities.Extras Extras;
    private String PackageId;
    private String TellerId;
    private String CustomerName;
    private  Integer SchoolCode;
    private String terminal_transaction_id;

    //default constructor
    public UtilityRequestData(){

    }

    //generate getters and setters

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

    public String getTransrefNo() {
        return TransrefNo;
    }

    public void setTransrefNo(String transrefNo) {
        TransrefNo = transrefNo;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public compas.models.utilities.Extras getExtras() {
        return Extras;
    }

    public void setExtras(compas.models.utilities.Extras extras) {
        Extras = extras;
    }

    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

    public String getTellerId() {
        return TellerId;
    }

    public void setTellerId(String tellerId) {
        TellerId = tellerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Integer getSchoolCode() {
        return SchoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        SchoolCode = schoolCode;
    }

    public String getTerminal_transaction_id() {
        return terminal_transaction_id;
    }

    public void setTerminal_transaction_id(String device_transaction_id) {
        this.terminal_transaction_id = terminal_transaction_id;
    }
}
