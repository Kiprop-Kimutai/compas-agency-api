package compas.agent;

import compas.models.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public class AgentResponse {
    private  Device device;
    private Issued_Device issued_device;
    private Agent agent;
    private User  user;
    private Bank bank;
    private Bank_Branch branch;
    private List<Account> accounts;
    private List<Transaction_Type>transaction_types;
    private List<Transaction_Operation> transaction_operations;
    private List<Transaction_Mode> transaction_modes;
    private List<Auth_Mode> auth_modes;
    private List<Currency> currencies;

    //default constructor
    public AgentResponse(){}

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Issued_Device getIssued_device() {
        return issued_device;
    }

    public void setIssued_device(Issued_Device issued_device) {
        this.issued_device = issued_device;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank_Branch getBranch() {
        return branch;
    }

    public void setBranch(Bank_Branch branch) {
        this.branch = branch;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction_Type> getTransaction_types() {
        return transaction_types;
    }

    public void setTransaction_types(List<Transaction_Type> transaction_types) {
        this.transaction_types = transaction_types;
    }

    public List<Transaction_Mode> getTransaction_modes() {
        return transaction_modes;
    }

    public List<Transaction_Operation> getTransaction_operations() {
        return transaction_operations;
    }

    public void setTransaction_operations(List<Transaction_Operation> transaction_operations) {
        this.transaction_operations = transaction_operations;
    }

    public void setTransaction_modes(List<Transaction_Mode> transaction_modes) {
        this.transaction_modes = transaction_modes;
    }

    public List<Auth_Mode> getAuth_modes() {
        return auth_modes;
    }

    public void setAuth_modes(List<Auth_Mode> auth_modes) {
        this.auth_modes = auth_modes;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
