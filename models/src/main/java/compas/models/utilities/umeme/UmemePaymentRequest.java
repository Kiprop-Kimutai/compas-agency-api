package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemePaymentRequest {
    private String Username;
    private String Action;
    private UmemePaymentData Data;

    //default construct
    public UmemePaymentRequest(){

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

    public UmemePaymentData getData() {
        return Data;
    }

    public void setData(UmemePaymentData data) {
        Data = data;
    }
}
