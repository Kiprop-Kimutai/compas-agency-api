package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscAreasRequest {
    private String Username;
    private String Action;
    private NwscAreasRequestData Data;

    //default constructor
    public NwscAreasRequest(){}

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

    public NwscAreasRequestData getData() {
        return Data;
    }

    public void setData(NwscAreasRequestData data) {
        Data = data;
    }
}
