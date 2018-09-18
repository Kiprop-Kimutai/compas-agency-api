package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
@Entity
public class Account_Transaction_Limits {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer transaction_operation_id;
    private Integer transaction_limit;

    //default constructor
    public Account_Transaction_Limits(){}

    //getters and setters

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTransaction_operation_type_id() {
        return transaction_operation_id;
    }

    public void setTransaction_operation_type_id(Integer transaction_operation_type_id) {
        this.transaction_operation_id = transaction_operation_type_id;
    }


    public Integer getTransaction_limit() {
        return transaction_limit;
    }

    public void setTransaction_limit(Integer transaction_limit) {
        this.transaction_limit = transaction_limit;
    }
}
