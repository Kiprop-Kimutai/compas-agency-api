package compas.models.bankoperations.reversals;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class ReversalRequest {
    private String Username;
    private String Action;
    private ReversalRequestData Data;

    //default constrcutor
    public ReversalRequest(){}

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

    public ReversalRequestData getData() {
        return Data;
    }

    public void setData(ReversalRequestData data) {
        Data = data;
    }
}
