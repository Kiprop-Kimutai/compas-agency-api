package compas.models.utilities;

/**
 * Created by CLLSDJACKT013 on 9/19/2018.
 */
public class GOTV {
    private String [] Top_up_bouquet = {"validate customer", "make payment"};
    private String [] Choose_GOTV_bouquet = {"Retrieve bouquet list", "retrieve period being paid for", "retrieve period 2 list","retrieve period 3 list", "Retrieve annual perod list", "Validate customer","make payment"};

    //default constructor
    public GOTV(){

    }

    //generate getter and setters


    public String[] getTop_up_bouquet() {
        return Top_up_bouquet;
    }

    public void setTop_up_bouquet(String[] top_up_bouquet) {
        Top_up_bouquet = top_up_bouquet;
    }

    public String[] getChoose_GOTV_bouquet() {
        return Choose_GOTV_bouquet;
    }

    public void setChoose_GOTV_bouquet(String[] choose_GOTV_bouquet) {
        Choose_GOTV_bouquet = choose_GOTV_bouquet;
    }
}
