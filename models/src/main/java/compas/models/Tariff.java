package compas.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String name;
    public Integer transaction_type_id;
    private Integer operation_id;
    public Double min_amount;
    public Double max_amount;
    public Double  bank_income;
    public Double agent_income;
    public Double excise_duty;
    private Integer created_by;
    private Boolean status;

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

    public Integer getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(Integer operation_id) {
        this.operation_id = operation_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
