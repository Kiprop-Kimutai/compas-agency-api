package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
//@Document(collection = "card")
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String card_number;
    private String account_number;
    private Integer scheme_id;
    private Integer type_id;
    private String status;
    private String expiry;
    private Integer country_id;
    //default constructor
    public Card(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Integer  getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(Integer scheme_id) {
        this.scheme_id = scheme_id;
    }

    public Integer  getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer  country_id) {
        this.country_id = country_id;
    }

    public String getString(){
        return String.format("card[card_number = %s account_number = %s scheme_id = %s]",card_number,account_number,scheme_id);
    }
}
