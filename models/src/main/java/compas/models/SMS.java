package compas.models;

import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 7/3/2018.
 */
@Entity
public class SMS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer Id;
    public String PhoneNo;
    public String Narration;

    //default constructor
    public SMS(){

    }

    public SMS(String phoneNo, String narration) {
        PhoneNo = phoneNo;
        Narration = narration;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
    }
}
