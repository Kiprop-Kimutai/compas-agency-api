package compas.restservice;

/**
 * Created by CLLSDJACKT013 on 23/05/2018.
 */
public class ServerResponse {
    private Integer code;
    private String message;

    //default constructor
    public ServerResponse(){}
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
