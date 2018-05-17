package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
//@Document(collection = "account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String account_name;
    private Integer account_type_id;
    private String account_number;
    private String customer_id_number;
    private Integer created_by;
    private Boolean status;

    //default constructor
    public Account(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Integer getAccount_type_id() {
        return account_type_id;
    }

    public void setAccount_type_id(Integer account_type_id) {
        this.account_type_id = account_type_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCustomer_id_number() {
        return customer_id_number;
    }

    public void setCustomer_id_number(String  customer_id_number) {
        this.customer_id_number = customer_id_number;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getString(){
        return String.format("account[name = %s type = %s number = %s]",account_name,status,account_number);
    }
}
