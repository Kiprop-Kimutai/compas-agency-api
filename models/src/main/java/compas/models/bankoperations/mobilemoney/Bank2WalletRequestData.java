package compas.models.bankoperations.mobilemoney;

/**
 * Created by CLLSDJACKT013 on 8/17/2018.
 */
public class Bank2WalletRequestData {
    private String Amount;
    private String TransrefNo;
    private String PhoneNo;
    private String Narration;

    //default constructor
    public Bank2WalletRequestData(){

    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransrefNo() {
        return TransrefNo;
    }

    public void setTransrefNo(String transrefNo) {
        TransrefNo = transrefNo;
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
