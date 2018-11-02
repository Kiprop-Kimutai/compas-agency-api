package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscPaymentResponseData {
    private String Name;
    private String Balance;
    private String CustNo;
    private String Amount;
    private String Area;
    private String Notes;
    private String AgentCommission;
    private String ClientCharge;
    private String CustRef;
    private String ValidationResult;
    private String Phone;
    private String status;
    private String outTxId;
    private String TranId;
    //default constructor
    public NwscPaymentResponseData(){}
    //getters and setters here

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCustNo() {
        return CustNo;
    }

    public void setCustNo(String custNo) {
        CustNo = custNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getAgentCommission() {
        return AgentCommission;
    }

    public void setAgentCommission(String agentCommission) {
        AgentCommission = agentCommission;
    }

    public String getClientCharge() {
        return ClientCharge;
    }

    public void setClientCharge(String clientCharge) {
        ClientCharge = clientCharge;
    }

    public String getCustRef() {
        return CustRef;
    }

    public void setCustRef(String custRef) {
        CustRef = custRef;
    }

    public String getValidationResult() {
        return ValidationResult;
    }

    public void setValidationResult(String validationResult) {
        ValidationResult = validationResult;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutTxId() {
        return outTxId;
    }

    public void setOutTxId(String outTxId) {
        this.outTxId = outTxId;
    }

    public String getTranId() {
        return TranId;
    }

    public void setTranId(String tranId) {
        TranId = tranId;
    }
}
