package compas.bank;

import com.google.gson.Gson;
import compas.models.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/bank")
public class BankController {
    private Logger logger = LoggerFactory.getLogger(BankController.class);
    private Gson gson = new Gson();
    @Autowired(required = false)
    private BankRepository bankRepository;

    @RequestMapping(path="/addBank", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    public ResponseEntity saveBank(@RequestBody String bankString){
        Bank bank = bankRepository.save(gson.fromJson(bankString, Bank.class));
        return ResponseEntity.status(201).body(bank);
    }
}
