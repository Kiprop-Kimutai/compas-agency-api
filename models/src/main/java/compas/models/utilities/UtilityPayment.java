package compas.models.utilities;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 10/19/2018.
 */
@Entity()
public class UtilityPayment {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private Integer operational_id; //from device NOT NULL
    private Integer transaction_type_id; //from device NOT NULL
    private String utility_code;//from device NOT NULL
    private String utility;//watch this
    private String menu;//watch this
    private Integer mode_id; //from device NOT NULL
    private String account_from; //device NOT NULL
    private String account_to;  //device NOT NULL
    private Double agent_commision;//api NOT NULL
    private Double bank_income;//api NOT NULL
    private Double excise_duty;//api NOT NULL
    private String card_iccid;//device OPTIONAL
    private String transaction_date;//api NOT NULL
    private String receipt_number; //api NOT NULL
    private String cbs_trans_id; //corebanking API NOT NULL
    private Integer agent_id;// device NOT NULL
    private Integer auth_mode; //device NOT NULL
    private String authentication; //device RFU
    private String narration;//api NOT NULL
    private Integer currency_id;//device NOT NULL
    private String latitude;//device NOT NULL
    private String longitude;//device NOT NULL
    private String macaddress; //device NOT NULL
    private String RequestId;
    private String CustRef;//device NOT NULL
    private String PhoneNo; //device NOT NULL
    private String BranchId; //device
    private String Amount;
    private String TransId;//trans_id returned from core banking. POS device re-sends this
    private String TransrefNo;
    private String AreaId;
    @Transient
    private  Extras Extras;
    private String PackageId;
    private String TellerId;
    private String CustomerName;
    private  Integer SchoolCode;
    private String terminal_transaction_id; //device NOT NULL
    private String status;

    //default constructor
    public UtilityPayment (){}
    //getters and setters go here

 /*   public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }*/

    public Integer getOperational_id() {
        return operational_id;
    }

    public String getUtility_code() {
        return utility_code;
    }

    public void setUtility_code(String utility_code) {
        this.utility_code = utility_code;
    }

    public void setOperational_id(Integer operational_id) {
        this.operational_id = operational_id;

    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Integer transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public Integer getMode_id() {
        return mode_id;
    }

    public void setMode_id(Integer mode_id) {
        this.mode_id = mode_id;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }

    public Double getAgent_commision() {
        return agent_commision;
    }

    public void setAgent_commision(Double agent_commision) {
        this.agent_commision = agent_commision;
    }

    public Double getBank_income() {
        return bank_income;
    }

    public void setBank_income(Double bank_income) {
        this.bank_income = bank_income;
    }

    public Double getExcise_duty() {
        return excise_duty;
    }

    public void setExcise_duty(Double excise_duty) {
        this.excise_duty = excise_duty;
    }

    public String getCard_iccid() {
        return card_iccid;
    }

    public void setCard_iccid(String card_iccid) {
        this.card_iccid = card_iccid;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getCbs_trans_id() {
        return cbs_trans_id;
    }

    public void setCbs_trans_id(String cbs_trans_id) {
        this.cbs_trans_id = cbs_trans_id;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
        this.agent_id = agent_id;
    }

    public Integer getAuth_mode() {
        return auth_mode;
    }

    public void setAuth_mode(Integer auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Integer currency_id) {
        this.currency_id = currency_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

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

    public void setTerminal_transaction_id(String terminal_transaction_id) {
        this.terminal_transaction_id = terminal_transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
