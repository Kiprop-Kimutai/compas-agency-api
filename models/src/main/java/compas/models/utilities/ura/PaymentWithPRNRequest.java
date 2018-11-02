package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PaymentWithPRNRequest {
    private String Username;
    private String Action;
    private PaymentWithPRNRequestData Data;

    //default constructor
    public PaymentWithPRNRequest(){}
    //getters and setters here

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

    public PaymentWithPRNRequestData getData() {
        return Data;
    }

    public void setData(PaymentWithPRNRequestData data) {
        Data = data;
    }
}
