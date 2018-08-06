package compas.models;

/**
 * Created by CLLSDJACKT013 on 7/19/2018.
 */
public class CustomerBio {
    private String account_number;
    private Fingerprints fingerprints;

    //default constructor
    public CustomerBio(){

    }

    public CustomerBio(String account_number, Fingerprints fingerprints) {
        this.account_number = account_number;
        this.fingerprints = fingerprints;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Fingerprints getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(Fingerprints fingerprints) {
        this.fingerprints = fingerprints;
    }
}
