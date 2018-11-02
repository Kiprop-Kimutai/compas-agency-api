package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscPaymentRequest {
    private String Username;
    private String Action;
    private NwscPaymentRequestData Data;
    //default constructor
    public NwscPaymentRequest(){}
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

    public NwscPaymentRequestData getData() {
        return Data;
    }

    public void setData(NwscPaymentRequestData data) {
        Data = data;
    }
}
