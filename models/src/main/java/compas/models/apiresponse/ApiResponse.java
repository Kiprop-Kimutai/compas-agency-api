package compas.models.apiresponse;

/**
 * Created by CLLSDJACKT013 on 23/05/2018.
 */
public class ApiResponse {
    private Integer code;
    private String message;

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
