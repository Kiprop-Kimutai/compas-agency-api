package compas.models.apiresponse;

import com.sun.org.apache.xpath.internal.operations.Bool;
import compas.models.*;
import compas.models.bankoperations.Inquiries.InquiriesResponseData;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 23/05/2018.
 */
public class ApiResponse {
    private Integer code;
    private String response_code;
    private String response_message;
    private Boolean response_status;
    public InquiriesResponseData Data;

    //default constructor
    public ApiResponse(){}

    public ApiResponse(Integer code, String response_code, String response_message, Boolean response_status, InquiriesResponseData data) {
        this.code = code;
        this.response_code = response_code;
        this.response_message = response_message;
        this.response_status = response_status;
        Data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return response_message;
    }

    public void setMessage(String response_message) {
        this.response_message = response_message;
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

    public Boolean getResponse_status() {
        return response_status;
    }

    public void setResponse_status(Boolean response_status) {
        this.response_status = response_status;
    }

    public InquiriesResponseData getData() {
        return Data;
    }

    public void setData(InquiriesResponseData data) {
        Data = data;
    }

    public String getString(){
        return String.format("api_response{code:%d response_code:%s response_message:%s data:%s}",code,response_code,response_message,Data.getString());
    }

}
