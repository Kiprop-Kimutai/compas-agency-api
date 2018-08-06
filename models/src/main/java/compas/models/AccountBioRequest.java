package compas.models;

/**
 * Created by CLLSDJACKT013 on 7/19/2018.
 */
public class AccountBioRequest {
    private String account_number;
    private String account_name;

    //default constructor
    public AccountBioRequest(){

    }

    public AccountBioRequest(String account_number, String account_name) {
        this.account_number = account_number;
        this.account_name = account_name;
    }

    //setters and getters here

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
}
