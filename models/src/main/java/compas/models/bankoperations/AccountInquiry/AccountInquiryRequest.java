package compas.models.bankoperations.AccountInquiry;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class AccountInquiryRequest {
    private String Username;
    private String Action;
    private ACRequestData Data;

    public AccountInquiryRequest(){}

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

    public ACRequestData getData() {
        return Data;
    }

    public void setData(ACRequestData data) {
        Data = data;
    }
}
