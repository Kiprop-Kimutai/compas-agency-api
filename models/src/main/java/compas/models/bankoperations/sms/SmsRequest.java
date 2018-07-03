package compas.models.bankoperations.sms;

import compas.models.SMS;

/**
 * Created by CLLSDJACKT013 on 7/3/2018.
 */
public class SmsRequest {
    public String Username;
    public String Action;
    public SMS Data;

    //default constructor
    public SmsRequest(){

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public SMS getData() {
        return Data;
    }

    public void setData(SMS data) {
        Data = data;
    }
}
