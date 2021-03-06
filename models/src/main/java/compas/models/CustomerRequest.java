package compas.models;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
public class CustomerRequest {
    private Customer customer;
    private Fingerprints fingerprints;
    private List<Account>accounts;
    private List<Next_of_Kin> next_of_kins;

    //default constructor
    public CustomerRequest(){}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Next_of_Kin> getNext_of_kins() {
        return next_of_kins;
    }

    public void setNext_of_kins(List<Next_of_Kin> next_of_kins) {
        this.next_of_kins = next_of_kins;
    }

    public Fingerprints getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(Fingerprints fingerprints) {
        this.fingerprints = fingerprints;
    }
}
