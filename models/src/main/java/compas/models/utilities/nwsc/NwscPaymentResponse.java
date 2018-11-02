package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscPaymentResponse {
    private String response_code;
    private Boolean response_status;
    private String response_message;
    private NwscPaymentResponseData Data;
    //default constructor
    public NwscPaymentResponse(){}
    //getters and setters

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

    public NwscPaymentResponseData getData() {
        return Data;
    }

    public void setData(NwscPaymentResponseData data) {
        Data = data;
    }
}
