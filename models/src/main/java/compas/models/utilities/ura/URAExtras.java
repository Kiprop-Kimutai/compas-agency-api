package compas.models.utilities.ura;

/**
 * Created by CLLSDJACKT013 on 10/12/2018.
 */
public class URAExtras {
    private String UraPrimaryPrn;
    private String BranchId;
    private Boolean HasPrn;
    private String PayerName;
    private String UraRef;
    private String PenaltyId;
    private String PenaltyTypeId;
    private String UraPlate;
    //default constructor
    public URAExtras(){}

    //generate getters and setters here

    public String getUraPrimaryPrn() {
        return UraPrimaryPrn;
    }

    public void setUraPrimaryPrn(String uraPrimaryPrn) {
        UraPrimaryPrn = uraPrimaryPrn;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public Boolean getHasPrn() {
        return HasPrn;
    }

    public void setHasPrn(Boolean hasPrn) {
        HasPrn = hasPrn;
    }

    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    public String getUraRef() {
        return UraRef;
    }

    public void setUraRef(String uraRef) {
        UraRef = uraRef;
    }

    public String getPenaltyId() {
        return PenaltyId;
    }

    public void setPenaltyId(String penaltyId) {
        PenaltyId = penaltyId;
    }

    public String getPenaltyTypeId() {
        return PenaltyTypeId;
    }

    public void setPenaltyTypeId(String penaltyTypeId) {
        PenaltyTypeId = penaltyTypeId;
    }

    public String getUraPlate() {
        return UraPlate;
    }

    public void setUraPlate(String uraPlate) {
        UraPlate = uraPlate;
    }
}
