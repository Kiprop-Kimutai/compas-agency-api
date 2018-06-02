package compas.models.bankoperations.deposit;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class DepositRequest {
    private String Username;
    private String Action;
    private DepositRequestData Data;

    //default constructor
    public DepositRequest(){}

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

    public DepositRequestData getData() {
        return Data;
    }

    public void setData(DepositRequestData data) {
        Data = data;
    }
}
