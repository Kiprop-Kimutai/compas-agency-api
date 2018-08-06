package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 8/3/2018.
 */
@Entity
public class Preferential_Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer agent_id;
    private String agent_code;
    private Integer transaction_operation_id;
    private Double favoured_percentage;

    //default constructor
    public Preferential_Tariff(){

    }

    public Preferential_Tariff(Integer agent_id, String agent_code, Integer transaction_operation_id, Double favoured_percentage) {
        this.agent_id = agent_id;
        this.agent_code = agent_code;
        this.transaction_operation_id = transaction_operation_id;
        this.favoured_percentage = favoured_percentage;
    }

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

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public Integer getTransaction_operation_id() {
        return transaction_operation_id;
    }

    public void setTransaction_operation_id(Integer transaction_operation_id) {
        this.transaction_operation_id = transaction_operation_id;
    }

    public Double getFavoured_percentage() {
        return favoured_percentage;
    }

    public void setFavoured_percentage(Double favoured_percentage) {
        this.favoured_percentage = favoured_percentage;
    }
}
