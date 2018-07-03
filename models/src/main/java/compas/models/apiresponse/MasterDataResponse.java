package compas.models.apiresponse;

import compas.models.bankoperations.Inquiries.InquiriesResponseData;

/**
 * Created by CLLSDJACKT013 on 6/29/2018.
 */
public class MasterDataResponse {
    private Integer code;
    private String response_code;
    private String response_message;
    private AgentResponse Data;

    //default constructor
    public MasterDataResponse(){}

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

    public AgentResponse getData() {
        return Data;
    }

    public void setData(AgentResponse data) {
        Data = data;
    }
}
