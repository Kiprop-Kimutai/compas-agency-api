package compas.models.bankoperations.Inquiries;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public class InquiriesResponseData {
    private String TransId;
    private String Balance;
    private String Amount;
    private String Account;
    private String FromAccount;
    private String ToAccount;
    private String [] Transactions;
    private String receipt_number;
    private Double agent_commission;
    private Double bank_income;
    private Double exercise_duty;
    private String workClass;
    private String tellerLimit;
    private String tellerAcct;

    public String Phone;
    public String AcctName;
    public String Email;
    public String ImageData;
    public String Schemecode;
    public String ActiveStatus;
    public String ClosingStatus;
    public String SolID;
    public String PBUNo;

    private BatchBalances[] Balances;

    //default constructor
    public InquiriesResponseData(){}

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String[] getTransactions() {
        return Transactions;
    }

    public void setTransactions(String[] transactions) {
        Transactions = transactions;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getFromAccount() {
        return FromAccount;
    }

    public void setFromAccount(String fromAccount) {
        FromAccount = fromAccount;
    }

    public String getToAccount() {
        return ToAccount;
    }

    public void setToAccount(String toAccount) {
        ToAccount = toAccount;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public Double getAgent_commission() {
        return agent_commission;
    }

    public void setAgent_commission(Double agent_commission) {
        this.agent_commission = agent_commission;
    }

    public Double getBank_income() {
        return bank_income;
    }

    public void setBank_income(Double bank_income) {
        this.bank_income = bank_income;
    }

    public Double getExercise_duty() {
        return exercise_duty;
    }

    public void setExercise_duty(Double exercise_duty) {
        this.exercise_duty = exercise_duty;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAcctName() {
        return AcctName;
    }

    public void setAcctName(String acctName) {
        AcctName = acctName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }

    public String getSchemecode() {
        return Schemecode;
    }

    public void setSchemecode(String schemecode) {
        Schemecode = schemecode;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getClosingStatus() {
        return ClosingStatus;
    }

    public void setClosingStatus(String closingStatus) {
        ClosingStatus = closingStatus;
    }

    public String getSolID() {
        return SolID;
    }

    public void setSolID(String solID) {
        SolID = solID;
    }

    public String getPBUNo() {
        return PBUNo;
    }

    public void setPBUNo(String PBUNo) {
        this.PBUNo = PBUNo;
    }

    public BatchBalances[] getBalances() {
        return Balances;
    }

    public void setBalances(BatchBalances[] balances) {
        Balances = balances;
    }

    public String getWorkClass() {
        return workClass;
    }

    public void setWorkClass(String workClass) {
        this.workClass = workClass;
    }

    public String getTellerLimit() {
        return tellerLimit;
    }

    public void setTellerLimit(String tellerLimit) {
        this.tellerLimit = tellerLimit;
    }

    public String getTellerAcct() {
        return tellerAcct;
    }

    public void setTellerAcct(String tellerAcct) {
        this.tellerAcct = tellerAcct;
    }

    public String getString(){
        return String.format("responseData{TransId:%s  Balance:%s  Account:%s  Amount :%s from :%s  to :%s}",TransId,Balance,Account,Amount,FromAccount,ToAccount);
    }
}
