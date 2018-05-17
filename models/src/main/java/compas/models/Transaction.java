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
@Entity
//@Document(collection = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
	private Integer transaction_type_id;
    private Integer operational_id;
    private Integer mode_id;
    private String account_number;
    private String card_number;
    private String amount;
    private String card_iccid;
    private String agent_code;
    private String transaction_date;
    private String receipt_number;
    private String currency;
    private String latitude;
    private String longitude;

    public Transaction(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Integer transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public Integer getOperational_id() {
        return operational_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOperational_id(Integer operational_id) {
        this.operational_id = operational_id;
    }

    public Integer getMode_id() {
        return mode_id;
    }

    public void setMode_id(Integer mode_id) {
        this.mode_id = mode_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCard_iccid() {
        return card_iccid;
    }

    public void setCard_iccid(String card_iccid) {
        this.card_iccid = card_iccid;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getString(){
        return String.format("[transaction[transaction_type_id = %d account_number = %s card_number = %s]",transaction_type_id,account_number,card_number);
    }
}
