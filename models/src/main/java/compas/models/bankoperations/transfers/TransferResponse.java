package compas.models.bankoperations.transfers;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class TransferResponse {
    private String response_code;
    private Boolean response_status;
    private String response_mesage;
    private TransferResponseData Data;

    //default constructor
    public TransferResponse(){}

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

    public String getResponse_mesage() {
        return response_mesage;
    }

    public void setResponse_mesage(String response_mesage) {
        this.response_mesage = response_mesage;
    }

    public TransferResponseData getData() {
        return Data;
    }

    public void setData(TransferResponseData data) {
        Data = data;
    }
}
