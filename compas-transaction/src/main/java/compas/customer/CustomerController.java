package compas.customer;

import com.google.gson.Gson;
import compas.accounts.AccountsRepository;
import compas.models.*;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.BiosResponse;
import compas.models.apiresponse.Message;
import compas.next_of_kin.NextOfKinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
@RestController
@RequestMapping(path="/compas/pbu/agency")
public class CustomerController {
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRDBMSRepository customerRDBMSRepository;
    @Autowired
    private FingerPrintRepository fingerPrintRepository;
    @Autowired
    private NextOfKinRepository nextOfKinRepository;
    private CustomerRepository customerRepository = new CustomerRepository();
    private Gson gson = new Gson();
    private BiosResponse apiResponse = new BiosResponse();
    //private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CustomerController.class);
    Message responseMessage = new Message();

    @RequestMapping(path="/saveCustomer",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveCustomer(@RequestBody String customerString){
        logger.info(customerString);
        CustomerRequest customerRequest = gson.fromJson(customerString,CustomerRequest.class);
        Customer customer = customerRequest.getCustomer();
        List<Account>accounts = customerRequest.getAccounts();
        List<Next_of_Kin>next_of_kins = customerRequest.getNext_of_kins();
        Fingerprints fingerprints = customerRequest.getFingerprints();
        logger.info(customer.getString());
        //save customer in RDBMS repo
        customerRDBMSRepository.save(customer);
        //save fingerprints in fingerprints repo
        fingerPrintRepository.save(fingerprints);
        //save customer in Mongo cache DB
        customerRepository.saveCustomer(customerRequest);
        accounts.forEach((account)->{logger.info(account.getString());accountsRepository.save(account);});
        next_of_kins.forEach(next_of_kin -> {logger.info(next_of_kin.getString());nextOfKinRepository.save(next_of_kin);});
        return  ResponseEntity.status(201).body(gson.toJson(customer));
    }

    @RequestMapping(path ="/enrollCustomer",consumes = "application/json",produces = "application/json")
    public ResponseEntity enrollCustomer(@RequestBody String customerString){
        logger.info(customerString);
        CustomerRequest customerRequest = gson.fromJson(customerString,CustomerRequest.class);
                //SAVE CUSTOMER
        customerRDBMSRepository.save(customerRequest.getCustomer());
                //SAVE FINGERPRINTS
        fingerPrintRepository.save(customerRequest.getFingerprints());
                //SAVE ACCOUNTS
        customerRequest.getAccounts().forEach((account -> {accountsRepository.save(account);}));
                //SAVE NEXT OF KINS
        customerRequest.getNext_of_kins().forEach((next_of_kin -> {nextOfKinRepository.save(next_of_kin);}));
                //CACHE CUSTOMER REQUEST in Mongo
        customerRepository.saveCustomer(customerRequest);

        responseMessage.setMessage("capture success");
        return ResponseEntity.status(201).body(gson.toJson(responseMessage));
    }

    //GET ACTIVE CUSTOMER BY ID NUMBER
    public Customer findActiveCustomerByIdNumber(String id_number){
        return customerRDBMSRepository.findActiveCustomerByIDNumber(id_number);
    }

    /***GET CUSTOMER BIO BY ACCOUNT NUMBER**/
    @RequestMapping(path = "/fetchaccountbio",consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity getCustomerBio(@RequestBody  String account_bio_request){
        CustomerBio customerBio = new CustomerBio();
        AccountBioRequest accountBioRequest = gson.fromJson(account_bio_request,AccountBioRequest.class);
        CustomerRequest retrievedCustomer = customerRepository.getCustomerRequestByAccountNumber(accountBioRequest);
        if(retrievedCustomer.getFingerprints()!=null){
            customerBio.setAccount_number(retrievedCustomer.getAccounts().get(0).getAccount_number());
            customerBio.setFingerprints(retrievedCustomer.getFingerprints());
            apiResponse.setCode(401);
            apiResponse.setResponse_message("ACCOUNT BIO DOWNLOADED SUCCESSFULLY");
            apiResponse.setData(retrievedCustomer.getFingerprints());
            return ResponseEntity.status(401).body(gson.toJson(apiResponse));
        }
        apiResponse.setCode(402);
        apiResponse.setResponse_message("NO BIOS FOUND FOR ACCOUNT");
        return ResponseEntity.status(401).body(gson.toJson(apiResponse));
    }



}
