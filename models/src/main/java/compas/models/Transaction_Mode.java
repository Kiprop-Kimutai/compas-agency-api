package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
@Entity
public class Transaction_Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    public String name;

    //default constructor
    public Transaction_Mode(){}

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

    public String  getString(){
        return String.format("transaction_mode[ID :::=%d    name::::: = s]",Id,name);
    }
}
