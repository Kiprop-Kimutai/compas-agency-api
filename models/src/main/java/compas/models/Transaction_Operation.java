package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
@Entity
public class Transaction_Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String name;
    public Integer transaction_type_id;
    private Integer cash_flow_id;

    //default constructor
    public Transaction_Operation(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Integer transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public Integer getCash_flow_id() {
        return cash_flow_id;
    }

    public void setCash_flow_id(Integer cash_flow_id) {
        this.cash_flow_id = cash_flow_id;
    }
}
