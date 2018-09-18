package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
@Entity
public class Agent_Operations {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer agent_id;
    private Integer transaction_operation_id;

    //default constructor
    public Agent_Operations(){}

    //getters and setters

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
        this.agent_id = agent_id;
    }

    public Integer getTransaction_operation_id() {
        return transaction_operation_id;
    }

    public void setTransaction_operation_id(Integer transaction_operation_id) {
        this.transaction_operation_id = transaction_operation_id;
    }
}
