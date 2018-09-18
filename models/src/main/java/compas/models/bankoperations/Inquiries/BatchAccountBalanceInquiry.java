package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 8/7/2018.
 */
public class BatchAccountBalanceInquiry {
    private String username;
    private String Action;
    private BatchAccountBalanceInquiryData Data;

    //default constructor
    public BatchAccountBalanceInquiry(){

    }

    //getters and setters here

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public BatchAccountBalanceInquiryData getData() {
        return Data;
    }

    public void setData(BatchAccountBalanceInquiryData data) {
        Data = data;
    }
}
