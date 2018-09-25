package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemeValidateRequest {
    private String Username;
    private String Action;
    private UmemeValidateData Data;

    //default constructor
    public UmemeValidateRequest(){

    }

    //getters and setters

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

    public UmemeValidateData getData() {
        return Data;
    }

    public void setData(UmemeValidateData data) {
        Data = data;
    }
}
