package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 10/22/2018.
 */
public class TellerAccountInquiryRequest {
    private String Username;
    private String Action;
    private TellerAccountInquiryData Data;
    //default constructor
    public TellerAccountInquiryRequest(){}
    //getters and setters here

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

    public TellerAccountInquiryData getData() {
        return Data;
    }

    public void setData(TellerAccountInquiryData data) {
        Data = data;
    }
}
