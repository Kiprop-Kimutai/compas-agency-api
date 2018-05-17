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
}
