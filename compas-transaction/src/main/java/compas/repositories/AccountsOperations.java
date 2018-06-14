package compas.repositories;

import compas.accounts.AccountsRepository;
import compas.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class AccountsOperations {
    @Autowired
    private AccountsRepository accountsRepository;


    public List<Account> findAccountByCustomerIdNumber(String id_number){
        return accountsRepository.findByIdNumber(id_number);
    }

    public Account findActiveAccountByAccountNumber(String account_number){
        return accountsRepository.findActiveAccountByAccountNumber(account_number);
    }

}
