package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 10/26/2018.
 */
@Entity
public class PSP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String psp_code;
    private String name;
    private Integer status;
    private Integer created_by;
    private Date created_at;
    private String ura_pin;
    private Double debit_limit;
    private Integer district_id;
    private Integer territory_id;
    private String crb_certificate;
    private String verified;
    private Integer verified_by;
    private String vat_number;
    private String contact_number;
    private String postal_address;
    private String postal_code;
    private String postal_town;

    //default constructor
    public PSP(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPsp_code() {
        return psp_code;
    }

    public void setPsp_code(String psp_code) {
        this.psp_code = psp_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getUra_pin() {
        return ura_pin;
    }

    public void setUra_pin(String ura_pin) {
        this.ura_pin = ura_pin;
    }

    public Double getDebit_limit() {
        return debit_limit;
    }

    public void setDebit_limit(Double debit_limit) {
        this.debit_limit = debit_limit;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public Integer getTerritory_id() {
        return territory_id;
    }

    public void setTerritory_id(Integer territory_id) {
        this.territory_id = territory_id;
    }

    public String getCrb_certificate() {
        return crb_certificate;
    }

    public void setCrb_certificate(String crb_certificate) {
        this.crb_certificate = crb_certificate;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Integer getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(Integer verified_by) {
        this.verified_by = verified_by;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
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

    public String getPostal_town() {
        return postal_town;
    }

    public void setPostal_town(String postal_town) {
        this.postal_town = postal_town;
    }
}
