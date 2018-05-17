package compas.customer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import compas.accounts.AccountsRepository;
import compas.models.Account;
import compas.models.Customer;
import compas.models.CustomerRequests;
import compas.models.Next_of_Kin;
import compas.next_of_kin.NextOfKinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
@RestController
@RequestMapping(path="/api/compas/pbu/agency")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private NextOfKinRepository nextOfKinRepository;
    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping(path="/saveCustomer",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveCustomer(@RequestBody String customerString){
        logger.info(customerString);
        CustomerRequests customerRequests = gson.fromJson(customerString,CustomerRequests.class);
        Customer customer = customerRequests.getCustomer();
        List<Account>accounts = customerRequests.getAccounts();
        List<Next_of_Kin>next_of_kins = customerRequests.getNext_of_kins();
        logger.info(customer.getString());
        customerRepository.save(customer);
        accounts.forEach((account)->{logger.info(account.getString());accountsRepository.save(account);});
        next_of_kins.forEach(next_of_kin -> {logger.info(next_of_kin.getString());nextOfKinRepository.save(next_of_kin);});
        return  ResponseEntity.status(201).body(gson.toJson(customer));
    }
}
