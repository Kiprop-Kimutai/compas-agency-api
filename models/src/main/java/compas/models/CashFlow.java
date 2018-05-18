package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 18/05/2018.
 */
@Entity
public class CashFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private   Integer Id;
    public  Double inflow;
    public  Double outflow;
    public  String receipt_number;
    public Long    timestamp;

    //default constructor

    public CashFlow(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getInflow() {
        return inflow;
    }

    public void setInflow(Double inflow) {
        this.inflow = inflow;
    }

    public Double getOutflow() {
        return outflow;
    }

    public void setOutflow(Double outflow) {
        this.outflow = outflow;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
