package compas.models.bankoperations.withdrawal;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class WithdrawalRequest {
    private String Username;
    private String Action;
    private WithdrawalRequestData Data;

    //default constructor
    public WithdrawalRequest(){}

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

    public WithdrawalRequestData getData() {
        return Data;
    }

    public void setData(WithdrawalRequestData data) {
        Data = data;
    }
}
