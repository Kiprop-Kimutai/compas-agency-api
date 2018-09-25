package compas.models.utilities.umeme;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class UmemePaymentResponseData {
    private String Name;
    private String Balance;
    private String MeterNo;
    private String Amount;
    private String ServiceFee;
    private String Inflation;
    private String Fx;
    private String Fuel;
    private String DebtRecovery;
    private String EnergyToken;
    private String ReceiptNumber;
    private String TokenValue;
    private String Units;
    private String Tax;
    private String LifeLine1;
    private String notes;
    private Integer status;
    private String outTxId;
    private String TranId;

    //default constructor

    public UmemePaymentResponseData(){

    }

    //getters and setters


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getMeterNo() {
        return MeterNo;
    }

    public void setMeterNo(String meterNo) {
        MeterNo = meterNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getServiceFee() {
        return ServiceFee;
    }

    public void setServiceFee(String serviceFee) {
        ServiceFee = serviceFee;
    }

    public String getInflation() {
        return Inflation;
    }

    public void setInflation(String inflation) {
        Inflation = inflation;
    }

    public String getFx() {
        return Fx;
    }

    public void setFx(String fx) {
        Fx = fx;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getDebtRecovery() {
        return DebtRecovery;
    }

    public void setDebtRecovery(String debtRecovery) {
        DebtRecovery = debtRecovery;
    }

    public String getEnergyToken() {
        return EnergyToken;
    }

    public void setEnergyToken(String energyToken) {
        EnergyToken = energyToken;
    }

    public String getReceiptNumber() {
        return ReceiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        ReceiptNumber = receiptNumber;
    }

    public String getTokenValue() {
        return TokenValue;
    }

    public void setTokenValue(String tokenValue) {
        TokenValue = tokenValue;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getLifeLine1() {
        return LifeLine1;
    }

    public void setLifeLine1(String lifeLine1) {
        LifeLine1 = lifeLine1;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOutTxId() {
        return outTxId;
    }

    public void setOutTxId(String outTxId) {
        this.outTxId = outTxId;
    }

    public String getTranId() {
        return TranId;
    }

    public void setTranId(String tranId) {
        TranId = tranId;
    }
}
