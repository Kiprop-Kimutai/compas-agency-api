package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class BranchRequestResponse {
    private String response_code;
    private Boolean response_status;
    private String response_message;
    private BranchRequestResponseData [] Data;

    //default constructor
    public BranchRequestResponse(){}

    //getters and setters here

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

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public BranchRequestResponseData[] getData() {
        return Data;
    }

    public void setData(BranchRequestResponseData[] data) {
        Data = data;
    }
}
