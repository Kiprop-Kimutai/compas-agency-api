package compas.models.utilities.ura;

import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PaymentWithoutPRNResponseData {
    private Date UraRegDate;
    private Date PrimaryPaydate;
    private Boolean valid;
    private String notes;
    private String request_id;
    private String name;
    private String AgentCommission;
    private String BranchId;
    private String outTxId;
    private String TranId;
    //default constructor
    public PaymentWithoutPRNResponseData(){}
    //generate getters and setters

    public Date getUraRegDate() {
        return UraRegDate;
    }

    public void setUraRegDate(Date uraRegDate) {
        UraRegDate = uraRegDate;
    }

    public Date getPrimaryPaydate() {
        return PrimaryPaydate;
    }

    public void setPrimaryPaydate(Date primaryPaydate) {
        PrimaryPaydate = primaryPaydate;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentCommission() {
        return AgentCommission;
    }

    public void setAgentCommission(String agentCommission) {
        AgentCommission = agentCommission;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
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
