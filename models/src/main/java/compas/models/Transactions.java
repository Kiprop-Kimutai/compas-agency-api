package compas.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Entity
//@Document(collection = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer Id;
    public Integer operational_id;
    public Integer mode_id;
    public String account_from;
    public String account_to;
    public String reference_account;
    public Integer amount;
    public Double agent_commision;
    public Double bank_income;
    public Double excise_duty;
    public String card_iccid;
    public Integer agent_id;
    public Integer auth_mode;
    public String authentication;
    public String transaction_date;
    public String receipt_number;
    public String cbs_trans_id;
    public String narration;
    public Integer currency_id;
    public Double cash_in;
    public Double cash_out;
    public String latitude;
    public String longitude;
    public String macaddress;
    public String phone;
    private Integer created_by;
    @Column(updatable = true)
    public String  status;

    public Transactions(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getOperational_id() {
        return operational_id;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Integer currency_id) {
        this.currency_id = currency_id;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCard_iccid() {
        return card_iccid;
    }

    public void setCard_iccid(String card_iccid) {
        this.card_iccid = card_iccid;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_Id) {
        this.agent_id = agent_id;
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

    public String getCbs_trans_id() {
        return cbs_trans_id;
    }

    public void setCbs_trans_id(String cbs_trans_id) {
        this.cbs_trans_id = cbs_trans_id;
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

    public Double getAgent_commision() {
        return agent_commision;
    }

    public void setAgent_commision(Double agent_commision) {
        this.agent_commision = agent_commision;
    }

    public Double getBank_income() {
        return bank_income;
    }

    public void setBank_income(Double bank_income) {
        this.bank_income = bank_income;
    }

    public Double getExcise_duty() {
        return excise_duty;
    }

    public void setExcise_duty(Double excise_duty) {
        this.excise_duty = excise_duty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String  status) {
        this.status = status;
    }

    public Integer getAuth_mode() {
        return auth_mode;
    }

    public void setAuth_mode(Integer auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthenticatation(String authentication) {
        this.authentication = authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public Double getCash_in() {
        return cash_in;
    }

    public void setCash_in(Double cash_in) {
        this.cash_in = cash_in;
    }

    public Double getCash_out() {
        return cash_out;
    }

    public void setCash_out(Double cash_out) {
        this.cash_out = cash_out;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }

    public String getReference_account() {
        return reference_account;
    }

    public void setReference_account(String reference_account) {
        this.reference_account = reference_account;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getString(){
        return String.format("[transaction[operational_id = %d account_to = %s  account_from  = %s agent_id  = %d receipt_number = %s]",operational_id,account_to,account_from,agent_id,receipt_number);
    }
}
