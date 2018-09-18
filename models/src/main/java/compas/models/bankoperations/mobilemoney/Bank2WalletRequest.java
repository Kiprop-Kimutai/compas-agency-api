package compas.models.bankoperations.mobilemoney;

/**
 * Created by CLLSDJACKT013 on 8/17/2018.
 */
public class Bank2WalletRequest {
    private String Username;
    private String Action;
    private Bank2WalletRequestData Data;

    //default constructor
    public Bank2WalletRequest(){

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Bank2WalletRequestData getData() {
        return Data;
    }

    public void setData(Bank2WalletRequestData data) {
        Data = data;
    }
}
