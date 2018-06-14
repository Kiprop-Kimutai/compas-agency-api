package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 06/06/2018.
 */
@Entity
public class Utilities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private  String name;
    private String utility_code;
    private String account_number;
    public Boolean status;
    public Integer created_by;

    //default constructor for JPA
    public Utilities(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUtility_code() {
        return utility_code;
    }

    public void setUtility_code(String utility_code) {
        this.utility_code = utility_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }
}
