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
import compas.txn_params.TransactionModeRepository;
import compas.txn_params.TransactionOperationRepository;
import compas.txn_params.TransactionTypeRepository;
import compas.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/api/agent")
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
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;
    @Autowired
    private TransactionModeRepository transactionModeRepository;
    @Autowired
    private AuthenticationModeRepository authenticationModeRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @RequestMapping(path="/fetchUsers",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    public ResponseEntity getAgent(@RequestBody String queryString){
        Device device = gson.fromJson(queryString,Device.class);
        /*
        -extract Device from DeviceRepository using  mac address
        -fetch Issued_Device details using device_id from Device
        -fetch Agent using agent_id from Issued_Device
        -fetch User using agent_id from Agent
        -fetch branch from Agent using Agent.branchId
        -fetch Bank from Branch using Bank_Branch.bank_id
        -fetch all accounts for agent using agent_id_number
        -fetch all transaction types
        -fetch  authentication modes
        -fetch currencies
        -finally build an Agent  response to with all Agent matrix members
        */
        Device requestedDevice = deviceRepository.findByMacAddress(device.getMacAddress());
        logger.info((requestedDevice.getString()));
       Issued_Device issued_device = issued_deviceRepository.findByDeviceId(requestedDevice.getId());
       logger.info(issued_device.getString());
       //String agentString = agentRepository.findAgentById(issued_device.getAgent_id());
        //         Agent agent = agentRepository.findById(issued_device.getAgent_id());
        Agent agent = agentRepository.findById(1);
       logger.info(agent.getString());
        User user = userRepository.findByAgentId(agent.getId());
        logger.info(user.getString());
        Bank_Branch branch = branchRepository.findBankById(agent.getBranch_id());
        logger.info(branch.getString());
        Bank bank = bankRepository.findBankById(branch.getId());
        logger.info(bank.getString());
        List<Account> accounts = accountsRepository.findByIdNumber(agent.getAgent_id_number());
        accounts.forEach((acc) ->{logger.info(acc.getString());});
        List<Transaction_Type> transaction_types = transactionTypeRepository.findAll();
        List<Transaction_Operation> transaction_operations = transactionOperationRepository.findAll();
        List<Transaction_Mode> transaction_modes = transactionModeRepository.findAll();
        List<Currency> currencies = currencyRepository.findAll();
        List<Auth_Mode> auth_modes = authenticationModeRepository.findAll();
        AgentResponse agentResponse = new AgentResponse();
        agentResponse.setAccounts(accounts);
        agentResponse.setAgent(agent);
        agentResponse.setBank(bank);
        agentResponse.setBranch(branch);
        agentResponse.setDevice(device);
        agentResponse.setIssued_device(issued_device);
        agentResponse.setUser(user);
        agentResponse.setTransaction_types(transaction_types);
        agentResponse.setTransaction_operations(transaction_operations);
        agentResponse.setTransaction_modes(transaction_modes);
        agentResponse.setAuth_modes(auth_modes);
        agentResponse.setCurrencies(currencies);
        return ResponseEntity.status(201).body(gson.toJson(agentResponse));
    }

    @RequestMapping(path="/addAgent",produces = "application/json",consumes = "application/json")
    @ResponseBody
    public ResponseEntity saveAgent(@RequestBody String agentString){
        Agent agent = gson.fromJson(agentString,Agent.class);
        Agent savedAgent = agentRepository.save(agent);
        return ResponseEntity.status(201).body(gson.toJson(savedAgent));
    }
}
