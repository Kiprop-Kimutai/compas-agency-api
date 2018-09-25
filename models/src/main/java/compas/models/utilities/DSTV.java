package compas.models.utilities;

/**
 * Created by CLLSDJACKT013 on 9/19/2018.
 */
public class DSTV {
    public String [] Top_up_bouquet = {"Validate customer", "Make payment"};
    //public String [] make_payment = {"Validate customer", "make payment"};
    //public String [] Get_DSTV_Feature_Options ={"Request list of feaure options", "Validate customer", "Make payment"};
    //public String [][] Retrieve_period = {make_payment,Get_DSTV_Feature_Options};
    //public String [][][]  Retrieve_other_bouqeuts = {Retrieve_period};
    public String [][]  Retrieve_other_bouqeuts = {{"Validate customer", "make payment"},{"Request list of feaure options", "Validate customer", "Make payment"}};


    //default constructor
    public DSTV(){

    }

    //generate getters and setters

    public String[] getTop_up_bouquet() {
        return Top_up_bouquet;
    }

    public void setTop_up_bouquet(String[] top_up_bouquet) {
        Top_up_bouquet = top_up_bouquet;
    }

    public String[][] getRetrieve_other_bouqeuts() {
        return Retrieve_other_bouqeuts;
    }

    public void setRetrieve_other_bouqeuts(String[][] retrieve_other_bouqeuts) {
        Retrieve_other_bouqeuts = retrieve_other_bouqeuts;
    }
}
