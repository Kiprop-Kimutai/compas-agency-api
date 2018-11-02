package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class PenaltyTypeRequest {
    private String Username;
    private String Action;
    private PenaltyTypeRequestData Data;
    //default constructor
    public PenaltyTypeRequest(){}
    //generate getters and setters

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

    public PenaltyTypeRequestData getData() {
        return Data;
    }

    public void setData(PenaltyTypeRequestData data) {
        Data = data;
    }
}
