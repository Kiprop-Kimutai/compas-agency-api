package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 7/12/2018.
 */
@Entity
public class Transaction_Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer transaction_operation_id;
    private Integer charge_type_id;
    private Double min_amount;
    private Double max_amount;
    private Double charge;


    //default constructor
    private Transaction_Charge(){}

    //constructor

    public Transaction_Charge(Integer transaction_operation_id, Integer charge_type_id, Double min_amount, Double max_amount, Double charge) {
        this.transaction_operation_id = transaction_operation_id;
        this.charge_type_id = charge_type_id;
        this.min_amount = min_amount;
        this.max_amount = max_amount;
        this.charge = charge;

    }

    //getters and setters here

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTransaction_operation_id() {
        return transaction_operation_id;
    }

    public void setTransaction_operation_id(Integer transaction_operation_id) {
        this.transaction_operation_id = transaction_operation_id;
    }

    public Integer getCharge_type_id() {
        return charge_type_id;
    }

    public void setCharge_type_id(Integer charge_type_id) {
        this.charge_type_id = charge_type_id;
    }

    public Double getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(Double min_amount) {
        this.min_amount = min_amount;
    }

    public Double getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(Double max_amount) {
        this.max_amount = max_amount;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

}
