package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
//@Document(collection = "bank_branch")
@Entity
public class Bank_Branch {
       @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer Id;
    	private String name;
    	@Column(name="bank_id")
        private Integer bankId;
        private String branch_code;
        private Boolean status;
        private String phone;
        private String address;
        private Integer  location_id;
        private Integer created_by;

        //default constructor
    public Bank_Branch(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bank_id) {
        this.bankId = bankId;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public String getString(){
        return String.format("branch[name = %s code =%s address = %s]",name,branch_code,address);
    }
}
