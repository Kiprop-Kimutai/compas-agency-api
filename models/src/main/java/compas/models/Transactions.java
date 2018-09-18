package compas.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public Integer transaction_type_id;
    public Integer mode_id;
    public String account_from;
    public String account_to;
    public String customer_name;
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
    public String latitude;
    public String longitude;
    public String macaddress;
    public String phone;
    private String finnacle_response_message;
    private String [] accounts_list;
    @Column(name = "from_date")
    public String From;
    @Column(name = "to_date")
    public String To;
    public String SchemeCode;
    public String utility_code;
    public String original_transId;
    public String terminal_trans_id;
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

    public Integer getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Integer transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public void setAgent_id(Integer agent_id) {
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

    public String getFinnacle_response_message() {
        return finnacle_response_message;
    }

    public void setFinnacle_response_message(String finnacle_response_message) {
        this.finnacle_response_message = finnacle_response_message;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getUtility_code() {
        return utility_code;
    }

    public void setUtility_code(String utility_code) {
        this.utility_code = utility_code;
    }

    public String[] getAccounts_list() {
        return accounts_list;
    }

    public void setAccounts_list(String[] accounts_list) {
        this.accounts_list = accounts_list;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getOriginal_transId() {
        return original_transId;
    }

    public void setOriginal_transId(String original_transId) {
        this.original_transId = original_transId;
    }

    public String getTerminal_trans_id() {
        return terminal_trans_id;
    }

    public void setTerminal_trans_id(String terminal_trans_id) {
        this.terminal_trans_id = terminal_trans_id;
    }

    public String getString(){
        return String.format("[transaction[operational_id = %d account_to = %s  account_from  = %s agent_id  = %d receipt_number = %s]",operational_id,account_to,account_from,agent_id,receipt_number);
    }


}
