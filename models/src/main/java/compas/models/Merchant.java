package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String merchant_code;
    private String merchant_description;
    private String  postal_address;
    private String postal_code;
    private String town;
    private Integer branch_id;
    private Boolean status;
    private Integer  created_by;
    private Integer verified_by;
    private String verified;
    private String crb_certificate;
    private String ura_pin;
    private String license_no;
    private Integer territory_id;
    private Integer district_id;

    //default constructor

    public Merchant(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_description() {
        return merchant_description;
    }

    public void setMerchant_description(String merchant_description) {
        this.merchant_description = merchant_description;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
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

    public Integer getTerritory_id() {
        return territory_id;
    }

    public void setTerritory_id(Integer territory_id) {
        this.territory_id = territory_id;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }
}
