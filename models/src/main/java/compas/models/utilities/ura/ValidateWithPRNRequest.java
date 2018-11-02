package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class ValidateWithPRNRequest {
    private String Username;
    private String Action;
    private ValidateWithPRNRequestData Data;

    //default constructor
    public ValidateWithPRNRequest(){}
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

    public ValidateWithPRNRequestData getData() {
        return Data;
    }

    public void setData(ValidateWithPRNRequestData data) {
        Data = data;
    }
}
