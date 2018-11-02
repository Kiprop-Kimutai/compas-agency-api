package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscValidateRequest {
    private String Username;
    private String Action;
    private NwscValidateRequestData Data;
    //default constructor
    public NwscValidateRequest(){}
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

    public NwscValidateRequestData getData() {
        return Data;
    }

    public void setData(NwscValidateRequestData data) {
        Data = data;
    }
}
