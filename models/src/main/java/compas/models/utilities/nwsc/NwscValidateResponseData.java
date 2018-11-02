package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class NwscValidateResponseData {
    private String custRef;
    private String area;
    private String notes;
    private String name;
    private String balance;
    //default constructor
    public NwscValidateResponseData(){}
    //getters and setters

    public String getCustRef() {
        return custRef;
    }

    public void setCustRef(String custRef) {
        this.custRef = custRef;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
