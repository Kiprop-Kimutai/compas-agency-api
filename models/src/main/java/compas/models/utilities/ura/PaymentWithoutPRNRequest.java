package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PaymentWithoutPRNRequest {
    private String Username;
    private String Action;
    private PaymentWithoutPRNRequestData Data;
    //default constructor
    public PaymentWithoutPRNRequest(){}
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

    public PaymentWithoutPRNRequestData getData() {
        return Data;
    }

    public void setData(PaymentWithoutPRNRequestData data) {
        Data = data;
    }
}
