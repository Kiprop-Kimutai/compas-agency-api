package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemeValidateResponseData {
    private String request_id;
    private String name;
    private String balance;

    //default constructor
    public UmemeValidateResponseData(){

    }

    //getters and setters here

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
