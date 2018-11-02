package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class ValidationWithoutPRNRequest {
    private String Username;
    private String Action;
    private ValidationWithoutPRNRequestData Data;
    //default constructor
    public ValidationWithoutPRNRequest(){}
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

    public ValidationWithoutPRNRequestData getData() {
        return Data;
    }

    public void setData(ValidationWithoutPRNRequestData data) {
        Data = data;
    }
}
