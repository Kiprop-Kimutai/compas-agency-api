package compas.agent;

import com.google.gson.Gson;
import compas.accounts.AccountsRepository;
import compas.authentications.AuthenticationModeRepository;
import compas.bank.BankRepository;
import compas.bank.BranchRepository;
import compas.currency.CurrencyRepository;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.*;
import compas.models.apiresponse.AgentResponse;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.MasterDataResponse;
import compas.txn_params.TransactionModeRepository;
import compas.txn_params.TransactionOperationController;
import compas.txn_params.TransactionTypeRepository;
import compas.user.UserRepository;
import compas.utilities.UtilitiesController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */

@RestController
@RequestMapping(path="/agent")
@Component
@Repository
public class AgentController {
    private Logger logger = LoggerFactory.getLogger(AgentController.class);
    private Gson gson = new Gson();
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
 /*   @Autowired
    private TransactionOperationRepository transactionOperationRepository;*/
 @Autowired(required = false)
    private TransOpRepository transOpRepository;
    @Autowired(required = false)
    private TransactionModeRepository transactionModeRepository;
    @Autowired
    private AuthenticationModeRepository authenticationModeRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private UtilitiesController utilitiesController;

    private TransactionOperationController transactionOperationController =  new TransactionOperationController();

    @RequestMapping(path="/fetchUsers",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    public ResponseEntity getAgent(@RequestBody String queryString){
        logger.info(queryString);
        Device device = gson.fromJson(queryString,Device.class);
        ApiResponse apiResponse = new ApiResponse();
        MasterDataResponse masterDataResponse = new MasterDataResponse();
        /*
        -extract Device from DeviceRepository using  mac address
        -fetch Issued_Device details using device_id from Device
        -fetch Agent using agent_id from Issued_Device
        -fetch Users using agent_id from Agent
        -fetch branch from Agent using Agent.branchId
        -fetch Bank from Branch using Bank_Branch.bank_id
        -fetch all accounts for agent using agent_id_number
        -fetch all transaction types
        -fetch Utility payments
        -fetch  authentication modes
        -fetch currencies
        -finally build an Agent  response to with all Agent matrix members
        */
        Device requestedDevice = deviceRepository.findByMacAddress(device.getMacAddress()) !=null ? deviceRepository.findByMacAddress(device.getMacAddress()) :new Device("xxx","",false,0);
        logger.info((requestedDevice.getString()));
       Issued_Device issued_device = issued_deviceRepository.findByDeviceId(requestedDevice.getId()) !=null ? issued_deviceRepository.findByDeviceId(requestedDevice.getId()): new Issued_Device(0,0,0,0);
       logger.info(issued_device.getString());
        /**IF DEVICE IS NOT REGISTERED**/
        if(requestedDevice.getMacAddress().equalsIgnoreCase("") && !requestedDevice.getStatus()){
            apiResponse.setCode(302);
            apiResponse.setMessage("device is not registered or is inactive");
            return ResponseEntity.status(201).body(gson.toJson(apiResponse));
        }
       //String agentString = agentRepository.findAgentById(issued_device.getAgent_id());
        //         Agent agent = agentRepository.findById(issued_device.getAgent_id());
        Agent agent = agentRepository.findById(issued_device.getAgent_id());
       logger.info(agent.getString());
        Users users = userRepository.findByAgentId(agent.getId());
        logger.info(users.getString());
        Bank_Branch branch = branchRepository.findBankById(agent.getBranch_id());
        logger.info(branch.getString());
        Bank bank = bankRepository.findBankById(branch.getBankId());
        logger.info(bank.getString());
        List<Account> accounts = accountsRepository.findByIdNumber(agent.getAgent_id_number());
        accounts.forEach((acc) ->{logger.info(acc.getString());});
        List<Transaction_Type> transaction_types = transactionTypeRepository.findAll();
        transaction_types.forEach((txn) ->{logger.info(txn.getString());});
        List<Transaction_Operation> transaction_operations = transOpRepository.findAll();
        //List<Transaction_Operation> transaction_operations = transactionOperationController.findAllTransactionOperations();
        //transaction_operations.forEach((ops) ->{logger.info(ops.logString());});
        List<Transaction_Mode> transaction_modes = transactionModeRepository.findAll();
        List<Utilities> utilities = utilitiesController.fetchAll();
        List<Currency> currencies = currencyRepository.findAll();
        List<Auth_Mode> auth_modes = authenticationModeRepository.findAll();
        auth_modes.forEach((auth_mode) ->{logger.info(auth_mode.getString());});
        AgentResponse agentResponse = new AgentResponse();
        agentResponse.setAccounts(accounts);
        agentResponse.setAgent(agent);
        agentResponse.setBank(bank);
        agentResponse.setBranch(branch);
        agentResponse.setDevice(device);
        agentResponse.setIssued_device(issued_device);
        agentResponse.setUsers(users);
        agentResponse.setTransaction_types(transaction_types);
        agentResponse.setTransaction_operations(transaction_operations);
        agentResponse.setTransaction_modes(transaction_modes);
        agentResponse.setUtilities(utilities);
        agentResponse.setAuth_modes(auth_modes);
        agentResponse.setCurrencies(currencies);
        /***BUILD MASTER DATA RESPONSE**/
        masterDataResponse.setCode(400);
        masterDataResponse.setResponse_message("SUCCESS");
        masterDataResponse.setData(agentResponse);
        logger.info("<<<<<MASTER DATA RESPONSE>>>>>");
        return ResponseEntity.status(201).body(gson.toJson(masterDataResponse));
    }

    @RequestMapping(path="/addAgent",produces = "application/json",consumes = "application/json")
    @ResponseBody
    public ResponseEntity saveAgent(@RequestBody String agentString){
        Agent agent = gson.fromJson(agentString,Agent.class);
        Agent savedAgent = agentRepository.save(agent);
        return ResponseEntity.status(201).body(gson.toJson(savedAgent));
    }

}
