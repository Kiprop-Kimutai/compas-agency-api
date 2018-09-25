package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemeValidateResponse {
    private  String response_code;
    private String response_status;
    private String response_message;
    private  UmemeValidateResponseData Data;

    //default constructor
    public UmemeValidateResponse(){}

    //getters and setters here


    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_status() {
        return response_status;
    }

    public void setResponse_status(String response_status) {
        this.response_status = response_status;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public UmemeValidateResponseData getData() {
        return Data;
    }

    public void setData(UmemeValidateResponseData data) {
        Data = data;
    }
}
