package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Entity //this tells Hibernate(H2) to create a table out of this class
//@Document(collection = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String first_name;
    private String surname;
    private String other_names;
    private String id_number;
    private String occupation;
    private Integer bank_id;
    private Integer branch_id;
    private String phone;
    private String email;
    private String address;

    public Customer(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        Id = Id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOther_names() {
        return other_names;
    }

    public void setOther_names(String other_names) {
        this.other_names = other_names;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getBank_id() {
        return bank_id;
    }

    public void setBank_id(Integer bank_id) {
        this.bank_id = bank_id;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String  getString(){
        return String.format("customer[firstname = %s  other names = %s   customer id = %s  occupation = %s bank = %d]",first_name,other_names,id_number,occupation,bank_id);
    }
}