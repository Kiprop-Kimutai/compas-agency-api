package compas.models.bankoperations.transfers;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class TransferRequest {
    private String Username;
    private String Action;
    private TransferRequestData Data;

    //default constructor
    public TransferRequest(){}

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

    public TransferRequestData getData() {
        return Data;
    }

    public void setData(TransferRequestData data) {
        Data = data;
    }
}
