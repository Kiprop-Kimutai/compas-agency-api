package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;


/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
//@Document(collection = "agent")
@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String agent_code;
    private String agent_description;
    private String agent_id_number;
    private String address;
    private String town;
    private Integer branch_id;
    private Boolean status;
    private Double deposit_limit;
    private Double withdrawal_limit;
    private Integer  created_by;
    //default constructor
    public Agent() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        Id = Id;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getAgent_description() {
        return agent_description;
    }

    public void setAgent_description(String agent_description) {
        this.agent_description = agent_description;
    }

    public String getAgent_id_number() {
        return agent_id_number;
    }

    public void setAgent_id_number(String agent_id_number) {
        this.agent_id_number = agent_id_number;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String gettown() {
        return town;
    }

    public void settown(String town) {
        this.town = town;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public Double getDeposit_limit() {
        return deposit_limit;
    }

    public void setDeposit_limit(Double deposit_limit) {
        this.deposit_limit = deposit_limit;
    }

    public Double getWithdrawal_limit() {
        return withdrawal_limit;
    }

    public void setWithdrawal_limit(Double withdrawal_limit) {
        this.withdrawal_limit = withdrawal_limit;
    }

    public String getString(){
        return String.format("agent[agent_code = %s description = %s  agent_id_number]",agent_code,agent_description);
    }
}
