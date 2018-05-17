package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
//@Document(collection = "account_type")
@Entity
public class Account_Type {
    @Id
    private Integer Id;
    private String name;

    //default constructor
    public Account_Type(){}

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
}
