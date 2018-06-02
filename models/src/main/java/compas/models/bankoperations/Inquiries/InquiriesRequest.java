package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class InquiriesRequest {
    private String Username;
    private String Action;
    private InquiriesRequestData Data;

    //default constructor
    public InquiriesRequest(){}

    //generate getter and setters here

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

    public InquiriesRequestData getData() {
        return Data;
    }

    public void setData(InquiriesRequestData data) {
        Data = data;
    }
}
