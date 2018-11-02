package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PayPenaltyRequest {
    private String Username;
    private String Action;
    private PayPenaltyRequestData Data;
    //default constructor
    public PayPenaltyRequest(){}
    //generate getters and setters here

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

    public PayPenaltyRequestData getData() {
        return Data;
    }

    public void setData(PayPenaltyRequestData data) {
        Data = data;
    }
}
