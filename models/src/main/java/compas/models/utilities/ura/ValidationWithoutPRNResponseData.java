package compas.models.utilities.ura;

import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class ValidationWithoutPRNResponseData {
    private Date UraRegDate;
    private Date PrimaryPaydate;
    private Boolean valid;
    private String notes;
    private String request_id;
    private String name;
    private Long balance;
    //default constructor
    public ValidationWithoutPRNResponseData(){}
    //getters and setters here

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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
