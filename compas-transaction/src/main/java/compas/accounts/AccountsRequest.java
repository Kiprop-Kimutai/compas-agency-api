package compas.accounts;

import compas.models.Account;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 11/05/2018.
 */
public class AccountsRequest {
    public List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
