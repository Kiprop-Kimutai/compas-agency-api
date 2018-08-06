package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 7/12/2018.
 */
@Entity
public class Transaction_Charge_Types {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer Id;
    public String name;

    //default constructor
    public Transaction_Charge_Types(){

    }

    public Transaction_Charge_Types(String name) {
        this.name = name;
    }
    //getter and setter

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
