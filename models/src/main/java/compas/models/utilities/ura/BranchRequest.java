package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class BranchRequest {
    private String Username;
    private String Action;
    private BranchRequestData Data;

    //default constructor
    public BranchRequest(){}

    //getters and setters go here

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

    public BranchRequestData getData() {
        return Data;
    }

    public void setData(BranchRequestData data) {
        Data = data;
    }
}
