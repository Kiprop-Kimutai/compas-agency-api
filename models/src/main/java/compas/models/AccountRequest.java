package compas.models;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 11/22/2018.
 */
@Entity
public class AccountRequest {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer Id;
    	private String first_name;
        private String other_name;
        private String account_type;
        private String branch;
        private String gender;
        private String dob;
        private String id_number;
        private String phone_number;
        @Column(columnDefinition = "NVARCHAR(MAX)")
        private String customer_photo;
        @Column(columnDefinition = "NVARCHAR(MAX)")
        private String customer_id;

        //default constructor
        public void AccountRequest (){

        }

        //generate getters and setters here


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCustomer_photo() {
        return customer_photo;
    }

    public void setCustomer_photo(String customer_photo) {
        this.customer_photo = customer_photo;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
