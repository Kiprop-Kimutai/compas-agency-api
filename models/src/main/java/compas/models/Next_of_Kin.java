package compas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Entity
public class Next_of_Kin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String name;
    private int  id_number;
    private String  phone;
    private  String address;
    private Integer  customer_id;

    public Next_of_Kin(){}

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public String getaddress() {
        return address;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer  getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer  customer_id) {
        this.customer_id = customer_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getString(){
        return String.format("next_of_kin[name = %s id_number = %s customer_id = %d]",name,id_number,customer_id);
    }
}
