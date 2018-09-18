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
    private String postal_address;
    private String postal_code;
    private String town;
    private String gender;
    private Integer branch_id;
    private Boolean status;
    private Double deposit_limit;
    private Double withdrawal_limit;
    private Double daily_limit;
    private Double weekly_limit;
    private Double monthly_limit;
    private Double quarterly_limit;
    private Integer  created_by;
    private Integer verified_by;
    private Boolean verified;
    //default constructor
    public Agent() {
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

    public void setId(Integer id) {
        Id = id;
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

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
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

    public Integer getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(Integer verified_by) {
        this.verified_by = verified_by;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getDaily_limit() {
        return daily_limit;
    }

    public void setDaily_limit(Double daily_limit) {
        this.daily_limit = daily_limit;
    }

    public Double getWeekly_limit() {
        return weekly_limit;
    }

    public void setWeekly_limit(Double weekly_limit) {
        this.weekly_limit = weekly_limit;
    }

    public Double getMonthly_limit() {
        return monthly_limit;
    }

    public void setMonthly_limit(Double monthly_limit) {
        this.monthly_limit = monthly_limit;
    }

    public Double getQuarterly_limit() {
        return quarterly_limit;
    }

    public void setQuarterly_limit(Double quarterly_limit) {
        this.quarterly_limit = quarterly_limit;
    }

    public String getString(){
        return String.format("agent[agent_code = %s description = %s  agent_id_number = %s branch id = %d, postal address = %s town =%s]",agent_code,agent_description,agent_id_number,branch_id,postal_address,town);
    }
}
