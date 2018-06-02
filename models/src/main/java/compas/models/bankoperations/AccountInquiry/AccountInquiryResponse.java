package compas.models.bankoperations.AccountInquiry;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class AccountInquiryResponse {
    private String response_code;
    private Boolean response_status;
    private ACResponseData Data;

    //default constructor
    public AccountInquiryResponse(){}

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public Boolean getResponse_status() {
        return response_status;
    }

    public void setResponse_status(Boolean response_status) {
        this.response_status = response_status;
    }

    public ACResponseData getData() {
        return Data;
    }

    public void setData(ACResponseData data) {
        Data = data;
    }
}
