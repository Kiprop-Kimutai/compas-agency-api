package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    public Integer transaction_type_id;
    public Double min_amount;
    public Double max_amount;
    public Double  bank_income;
    public Double agent_income;
    public Double excise_duty;

    //default constructor
    public Tariff(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Integer transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
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

    public Double getBank_income() {
        return bank_income;
    }

    public void setBank_income(Double bank_income) {
        this.bank_income = bank_income;
    }

    public Double getAgent_income() {
        return agent_income;
    }

    public void setAgent_income(Double agent_income) {
        this.agent_income = agent_income;
    }

    public Double getExcise_duty() {
        return excise_duty;
    }

    public void setExcise_duty(Double excise_duty) {
        this.excise_duty = excise_duty;
    }
}
