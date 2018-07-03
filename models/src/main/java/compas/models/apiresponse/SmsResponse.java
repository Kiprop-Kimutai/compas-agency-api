package compas.models.apiresponse;

/**
 * Created by CLLSDJACKT013 on 7/3/2018.
 */
public class SmsResponse {
    private Integer code;
    private String response_code;
    private String response_message;
    private SmsData Data;

    //default constructor
    public SmsResponse(){}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public SmsData getData() {
        return Data;
    }

    public void setData(SmsData data) {
        Data = data;
    }
}
