package compas.models;

/**
 * Created by CLLSDJACKT013 on 8/24/2018.
 */
public class ReversalRequest {
    private String terminal_trans_id;

    //default constructor
    public ReversalRequest(){}

    //getter and setter

    public String getTerminal_trans_id() {
        return terminal_trans_id;
    }

    public void setTerminal_trans_id(String terminal_trans_id) {
        this.terminal_trans_id = terminal_trans_id;
    }
}
