package compas.models.apiresponse;

import compas.models.Fingerprints;

/**
 * Created by CLLSDJACKT013 on 7/19/2018.
 */
public class BiosResponse {
    private Integer code;
    private String response_code;
    private String response_message;
    private Fingerprints Data;

    //default constructor
    public BiosResponse(){

    }

    //

    public BiosResponse(Integer code, String response_code, String response_message, Fingerprints data) {
        this.code = code;
        this.response_code = response_code;
        this.response_message = response_message;
        Data = data;
    }

    //getters and setters here

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

    public Fingerprints getData() {
        return Data;
    }

    public void setData(Fingerprints data) {
        Data = data;
    }
}
