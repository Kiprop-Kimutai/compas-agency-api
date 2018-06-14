package compas.accounts;

import com.google.gson.Gson;
import compas.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    private Logger logger  = LoggerFactory.getLogger(AccountsController.class);
    private Gson gson = new Gson();
    @Autowired
    private AccountsRepository accountsRepository;

    @RequestMapping(path="/addAcc",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveAccount(@RequestBody String accountString){
        AccountsRequest accountsRequest = gson.fromJson(accountString,AccountsRequest.class);
        List<Account> accs = accountsRequest.getAccounts();
        accs.forEach((account) ->{accountsRepository.save(account);});
        return ResponseEntity.status(201).body(gson.toJson(accountsRequest));
    }

    public String generateAccountNumber(String BIN,String branch_code,String id_number){
        String [] id_number_array = id_number.split("'");

        return "";
    }

    //FIND ACCOUNT BY ACCOUNT NUMBER
    public Account findActiveAccountByAccountNumber(String account_number){
        return accountsRepository.findActiveAccountByAccountNumber(account_number);
    }
}
