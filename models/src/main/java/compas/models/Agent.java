package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
@Entity
public class Agent {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer agent_type_id;
    private Integer merchant_id;
    private String agent_code;
    private String agent_description;
    private String crb_certificate;
    private String ura_pin;
    private String license_no;
    private String longitude;
    private String latitude;
    private Integer radius;
    private Integer branch_id;
    private Boolean status;
    private Double daily_limit;
    private Double weekly_limit;
    private Double monthly_limit;
    private Double quarterly_limit;
    private Integer  created_by;
    private Integer verified_by;
    private String verified;

    //default constructor
    public Agent(){}

    //setters and getters go here
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAgent_type_id() {
        return agent_type_id;
    }

    public void setAgent_type_id(Integer agent_type_id) {
        this.agent_type_id = agent_type_id;
    }

    public Integer getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(Integer merchant_id) {
        this.merchant_id = merchant_id;
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

    public String getCrb_certificate() {
        return crb_certificate;
    }

    public void setCrb_certificate(String crb_certificate) {
        this.crb_certificate = crb_certificate;
    }

    public String getUra_pin() {
        return ura_pin;
    }

    public void setUra_pin(String ura_pin) {
        this.ura_pin = ura_pin;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
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

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public Integer getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(Integer verified_by) {
        this.verified_by = verified_by;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
    public String getString(){
        return String.format("agent[agent_code = %s description = %s  branch id = %d]",agent_code,agent_description,branch_id);
    }
}
