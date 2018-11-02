package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 10/25/2018.
 */
@Entity
public class ApiSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String api_key;
    private String username;
    private String password;
    private byte[] hash;
    private String hash_string;
    private String action;

    //default constrcutor
    public ApiSecurity(){}
    //generate getters and setters


    public ApiSecurity(String api_key, String username, byte[] hash, String action) {
        this.api_key = api_key;
        this.username = username;
        this.hash = hash;
        this.action = action;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHash_string() {
        return hash_string;
    }

    public void setHash_string(String hash_string) {
        this.hash_string = hash_string;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return action;

    }

    public void setAction(String action) {
        this.action = action;
    }
}
