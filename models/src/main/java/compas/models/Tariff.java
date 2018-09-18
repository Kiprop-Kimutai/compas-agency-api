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
    private Integer transaction_operation_id;
    private Double bank_portion;
    private Double agent_portion;
    private Double excise_duty;
    private Integer status;
    private Integer created_by;

    //default constructor
    public Tariff(){

    }

    public Tariff(Integer transaction_operation_id, Double bank_portion, Double agent_portion, Double excise_duty) {
        this.transaction_operation_id = transaction_operation_id;
        this.bank_portion = bank_portion;
        this.agent_portion = agent_portion;
        this.excise_duty = excise_duty;
    }

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

    public Double getBank_portion() {
        return bank_portion;
    }

    public void setBank_portion(Double bank_portion) {
        this.bank_portion = bank_portion;
    }

    public Double getAgent_portion() {
        return agent_portion;
    }

    public void setAgent_portion(Double agent_portion) {
        this.agent_portion = agent_portion;
    }

    public Double getExcise_duty() {
        return excise_duty;
    }

    public void setExcise_duty(Double excise_duty) {
        this.excise_duty = excise_duty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }
}
