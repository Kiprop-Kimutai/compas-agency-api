package compas.models;

/**
 * Created by CLLSDJACKT013 on 9/28/2018.
 */
public class LoginPolicy {
    private String username;
    private String password;
    private String agent_code;
    private Integer count;
    private Integer operationId;

    //default constructor
    public LoginPolicy(){}

    //generate getters and setters here

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }
}
