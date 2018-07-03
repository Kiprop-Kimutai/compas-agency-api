package compas.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import compas.accounts.AccountsController;
import compas.agent.AgentRepository;
import compas.authentications.AuthenticationModeRepository;
import compas.authentications.ValidateTransactions;
import compas.customer.CustomerController;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.*;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.Message;
import compas.models.apiresponse.SmsResponse;
import compas.models.bankoperations.AccountInquiry.AccountInquiryResponse;
import compas.models.bankoperations.Inquiries.InquiriesRequestData;
import compas.models.bankoperations.Inquiries.InquiriesResponse;
import compas.models.bankoperations.Inquiries.InquiriesResponseData;
import compas.models.bankoperations.OperationEnums;
import compas.models.bankoperations.deposit.DepositResponse;
import compas.models.bankoperations.reversals.ReversalResponse;
import compas.models.bankoperations.transfers.TransferResponse;
import compas.models.bankoperations.withdrawal.WithdrawalRequest;
import compas.models.bankoperations.withdrawal.WithdrawalResponse;
import compas.repositories.RepositoryOperations;
import compas.repositories.TransactionOperations;
import compas.restservice.RestServiceConfig;
import compas.tariffs.TariffManager;
import compas.transaction.agencyreceipt.ReceiptManager;
import compas.transaction.passwordpolicy.OTPController;
import compas.transactionschannel.TransactionsToBank;
import compas.txn_params.InquiriesRequestDataRepository;
import compas.txn_params.TransactionOperationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Repository
@RestController
@RequestMapping(path="/compas/pbu/agency")
public class TransactionController {
    private Gson gson = new Gson();
    @Autowired
    private OTPController otpController;
    @Autowired
    private TariffManager tariffManager = new TariffManager();
    @Autowired
    private ReceiptManager receiptManager;
    @Autowired
    private ValidateTransactions validateTransactions;
    @Autowired
    private RestServiceConfig restServiceConfiguration;
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AuthenticationModeRepository authenticationModeRepository;
    @Autowired
    private TransactionOperationsRepository transactionOperationsRepository;
    @Autowired
    private InquiriesRequestDataRepository inquiriesRequestDataRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private TransactionsToBank transactionsToBank;
    private AccountsController accountsController = new AccountsController();
    private CustomerController customerController = new CustomerController();
    private TransactionRepository transactionRepository = new TransactionRepository();
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    OperationEnums opsenums =OperationEnums.DEPOSIT;
    String receipt_number = "";
    Issued_Device issued_device;
    Device device;
    Message responseMessage = new Message();
    ApiResponse apiResponse = new ApiResponse();
    SmsResponse smsResponse = new SmsResponse();
    // USE FALLBACK INSTANCES TO MANAGE NULL-RETURNED HIBERNATE QUERIES
    Account fallBackAccount = new Account();
    Customer fallBackCustomer = new Customer();

    private static String FILES_PATH = System.getProperty("catalina.base")+"/conf/";
    @Value("${api.username}")
    private String API_USERNAME;
    @Value("${SERVICE.IP}")
    public String SERVICE_IP;
    @Value("${SERVICE.PORT}")
    public String SERVICE_PORT;
    @Value("${SERVICE.ENDPOINT}")
    public String SERVICE_ENDPOINT;
    @Value("${api.protocol}")
    private String protocol;

    @RequestMapping(path="/test",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity test(){
        logger.info("CATALINA BASE::"+FILES_PATH);
        return ResponseEntity.ok("end point up...");
    }

    @Transactional
    @RequestMapping(path="/post_transaction",method=RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity postTransaction(@RequestBody String transactionString){
        logger.info(transactionString);
        Transactions transactions = gson.fromJson(transactionString,Transactions.class);
        Device wildDevice = new Device();
        try{
            wildDevice.setId(0);
            List<Issued_Device>issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transactions.getAgent_id());
             logger.info("Issued devices::"+issued_devices.size());
             if(issued_devices.size()>0){
                 issued_devices.forEach((x) ->{
                     issued_device = x;
                     logger.info("issued device");
                     logger.info(x.getString());
                     device = deviceRepository.findDeviceById(issued_device.getDeviceId())!=null ? deviceRepository.findDeviceById(issued_device.getDeviceId()) :wildDevice;
                     logger.info("issued device");
                     logger.info(device.getString());

                 });
             }
             else{
                // responseMessage.setMessage("DEVICE ISSUANCE ERROR.Device not issued to agent");
                 apiResponse.setCode(301);
                 apiResponse.setMessage("DEVICE IS  NOT ISSUED TO AGENT");
                 //logger.info(responseMessage.getMessage());
                 logger.info(gson.toJson(apiResponse));
                 return ResponseEntity.status(201).body(apiResponse);
             }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(transactions);
        }


        logger.info("Transactions Request::"+ transactions);
                    //FIRST CHECKPOINT>>>>>>VALIDATE TRANSACTION
        if(!validateTransactions.authenticateAgent(transactions.getAgent_id())){
            String message = String.format("Agent %d  is INACTIVE or NOT registered", transactions.getAgent_id());
            responseMessage.setMessage(message);
            apiResponse.setCode(300);
            apiResponse.setMessage(message);
            //logger.info(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            //return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id())){
            String message = String.format("Device  = %d  is not issued to agent %d ",issued_device.getDeviceId(),issued_device.getAgent_id());
            responseMessage.setMessage(message);
            apiResponse.setCode(301);
            apiResponse.setMessage(message);
            //logger.info(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            //return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(apiResponse);

        }
        if(!validateTransactions.authenticateDevice(device.getId())){
            String message = String.format("Device %s is not registered  or is inactive",device.getMacAddress());
            responseMessage.setMessage(message);
            apiResponse.setCode(302);
            apiResponse.setMessage(message);
            //logger.info(responseMessage.getMessage());
           // return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateTransactionLimits(transactions.getAmount()+0.0, transactions.getOperational_id(), transactions.getAgent_id())){
            responseMessage.setMessage(String.format("Transaction limits for operation type %s exceeded",transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction()));
            logger.info(responseMessage.getMessage());
            apiResponse.setCode(303);
            apiResponse.setMessage(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            //return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!(validateTransactions.authenticateAccount(transactions.getAccount_from())&& validateTransactions.authenticateAccount(transactions.getAccount_to()))){
            responseMessage.setMessage("Invalid account number");
            //logger.info(responseMessage.getMessage());
            apiResponse.setMessage("Invalid account number");
            apiResponse.setCode(304);
            logger.info(gson.toJson(apiResponse));
           // return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(apiResponse);

        }

        /*************************************************************************************************8/
   /*        if(validateTransactions.authenticateAgent(transactions.getAgent_id()) && validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id()) && validateTransactions.authenticateDevice(device.getId()) && validateTransactions.authenticateTransactionLimits(transactions.getAmount(),transactions.getOperational_id(),transactions.getAgent_id()) && validateTransactions.authenticateAccount(transactions.getAccount_to()) &&  validateTransactions.authenticateAccount(transactions.getAccount_from())){
                //generate receipt_number
                receipt_number = receiptManager.generateReceiptNumber(transactions.getAgent_id());
        }
        else {
            logger.info("TRANSACTION AUTH FAILED::::OVERALL FAILURE");
            responseMessage.setMessage("TRANSACTION AUTH FAILED");
            return ResponseEntity.status(400).body(gson.toJson(responseMessage));
        }
    */
        /**************************************************************************************************/
                    //GENERATE receipt_number
        receipt_number = receiptManager.generateReceiptNumber(transactions.getAgent_id());
                    //SET receipt number and narration
        transactions.setReceipt_number(receipt_number);
        transactions.setNarration(transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction());

                    //DETERMINE AND SET CASH INS and CASH OUTS
        Integer cash_flow_id = transactionOperationsRepository.selectCashFlowId(transactions.getOperational_id());
        if(cash_flow_id == 1){
            transactions.setCash_in(transactions.amount+0.0);
            transactions.setCash_out(0.0);
        }
        else if(cash_flow_id == 2){
            transactions.setCash_out(transactions.amount+0.0);
            transactions.setCash_in(0.0);
        }
                    //CHECK TRANSACTION AUTH MODE
        //String transactionMode = authenticationModeRepository.findAuth_ModeById(transactions.getAuth_mode()).getMode();
        String transactionMode = authenticationModeRepository.findAuth_ModeById(transactions.getAuth_mode()).getMode();
        logger.info("AUTHENTICATION:::",transactionMode);
        logger.info(transactionMode);
        if(transactionMode.equalsIgnoreCase("OTP")){
            //NOW GENERATE OTP and send to CBS
            String response = otpController.generateOTPPassword(transactions,receipt_number);
            //check response status
            //apiResponse = gson.fromJson(response,ApiResponse.class);
            smsResponse = gson.fromJson(response,SmsResponse.class);
            if(!smsResponse.getCode().equals("000")/*201*/){
                smsResponse.setCode(100);
                smsResponse.setResponse_message("OTP MESSAGE SERVER FAILED");
                //responseMessage.setMessage("OTP GENERATION FAILED");
                logger.info(gson.toJson(smsResponse));
                return ResponseEntity.status(201).body(smsResponse);
            }
            //update flag to "P"
            transactions.setStatus("P");
            //calculate tariffs and get processed transactions
            Transactions processedTransactions = tariffManager.setTariffCharges(transactions);
            transactionRepository.saveTransaction(processedTransactions);
            //Transactions validatedTransaction = transactionRDBMSRepository.save(processedTransactions);
            apiResponse.setCode(101);
            apiResponse.setMessage("OTP GENERATION SUCCESS");
            //responseMessage.setMessage("OTP generation success");
            //return ResponseEntity.status(201).body(gson.toJson(responseMessage));
            return ResponseEntity.status(201).body(apiResponse);

        }
        else if(transactionMode.equalsIgnoreCase("PIN")){
            transactions.setStatus("I");
            Transactions processedTransactions = tariffManager.setTariffCharges(transactions);
                            ///AUTHENTICATE TRANSACTION AT THIS POINT
            if(!validateTransactions.processAuthentication(transactions.getAuth_mode(),transactions.getAuthentication(),transactions.getAccount_from())){
                responseMessage.setMessage(String.format("PIN AUTHENTICATION FAILED FOR TRANSACTION %s FROM AGENT %s FOR ACCOUNT %s",transactions.getNarration(),agentRepository.findTransactingAgentById(transactions.getAgent_id()).getAgent_description(),transactions.getAccount_from()));
                //logger.info(responseMessage.getMessage());
                apiResponse.setCode(305);
                apiResponse.setMessage(responseMessage.getMessage());
                logger.info(gson.toJson(apiResponse));
                //return ResponseEntity.status(310).body(responseMessage.getMessage());
                return ResponseEntity.status(201).body(apiResponse);
            }
            transactionRepository.saveTransaction(processedTransactions);
            transactionRDBMSRepository.save(processedTransactions);

               /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
            String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            logger.info(responseData);
           // apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(processedTransactions),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id())),ApiResponse.class);
            apiResponse = gson.fromJson(responseData,ApiResponse.class);
            if(apiResponse.getCode() == 201){
                apiResponse = ProcessCBSResponse(processedTransactions.getReceipt_number(),apiResponse,"");

/*                            ///UPDATE TRANSACTION'S STATUS IN DB AFTER SUCCESSFUL TRANSFER
                logger.info("RECEIPT NUMBER TO UPDATE:::" + processedTransactions.getReceipt_number());
                transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                Transactions successfulProcessedTxn = transactionRepository.updateProcessedTransaction(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                apiResponse.setCode(150);
                apiResponse.setMessage(gson.toJson(successfulProcessedTxn));*/
                return ResponseEntity.status(201).body(apiResponse);
            }
                            //FOR UNSUCCESSFUL TRANSACTIONS, status to remain 'I' in DB
                return ResponseEntity.status(201).body(apiResponse);

        }
        else if(transactionMode.equalsIgnoreCase("BIO")){
            transactions.setStatus("I");
            Transactions processedTransactions = tariffManager.setTariffCharges(transactions);
                            //AUTHENTICATE TRANSACTION AT THIS POINT
            if(!validateTransactions.processAuthentication(transactions.getAuth_mode(),transactions.getAuthentication(),transactions.getAccount_from())){
                responseMessage.setMessage(String.format("BIO AUTHENTICATION FAILED FOR TRANSACTION %s FROM AGENT %s FOR ACCOUNT %s",transactions.getNarration(),agentRepository.findTransactingAgentById(transactions.getAgent_id()).getAgent_description(),transactions.getAccount_from()));
                logger.info(responseMessage.getMessage());
                apiResponse.setCode(306);
                apiResponse.setMessage(responseMessage.getMessage());
                //return ResponseEntity.status(201).body(responseMessage.getMessage());
                //return ResponseEntity.status(201).body(gson.toJson(apiResponse));
                return ResponseEntity.status(201).body(apiResponse);
            }
            transactionRepository.saveTransaction(processedTransactions);
            transactionRDBMSRepository.save(processedTransactions);
            //Transactions successfulTransaction = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(processedTransactions)),Transactions.class);

            /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
            String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            logger.info(responseData);
            //apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id())),ApiResponse.class);
            apiResponse = gson.fromJson(responseData,ApiResponse.class);
            if(apiResponse.getCode() ==201){
                apiResponse = ProcessCBSResponse(processedTransactions.getReceipt_number(),apiResponse,"");
/*                transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                Transactions verySuccessfulTxn = transactionRepository.updateProcessedTransaction(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                apiResponse.setCode(150);
                apiResponse.setMessage(gson.toJson(verySuccessfulTxn));
                //return ResponseEntity.status(201).body(verySuccessfulTxn);*/
                return ResponseEntity.status(201).body(apiResponse);

            }
            else{
                //return ResponseEntity.status(400).body(gson.toJson(apiResponse));
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));
            }
        }
        else{
                //transactions needing no AUTHENTICATION i.e cash deposits and Inquiries
            Transactions processedTransactions = tariffManager.setTariffCharges(transactions);
            transactionRepository.saveTransaction(processedTransactions);
            Transactions returnTx = transactionRDBMSRepository.save(processedTransactions);
            /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
            //String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(processedTransactions),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            logger.info(responseData);
            apiResponse = gson.fromJson(responseData,ApiResponse.class);
            logger.info(gson.toJson(apiResponse));

            //apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id())),ApiResponse.class);
            if(apiResponse.getCode() == 201){
                apiResponse = ProcessCBSResponse(processedTransactions.getReceipt_number(),apiResponse,"");
/*                transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                Transactions verySuccessfulTxn = transactionRepository.updateProcessedTransaction(processedTransactions.getReceipt_number(),apiResponse.getData().getTransId());
                apiResponse.setCode(150);
                apiResponse.setMessage(gson.toJson(verySuccessfulTxn));*/
                //return ResponseEntity.status(201).body(gson.toJson(verySuccessfulTxn));
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));

            }
            else{
                //apiResponse.setMessage(apiResponse.getMessage());
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));
            }

        }
    }

    @Transactional
    @RequestMapping(path="/inquiries",method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity postInquiries(@RequestBody String TransactionRequest){
        logger.info(TransactionRequest);
        logger.info(TransactionRequest);
               /****===============Model inquiries request data from Transaction Payload==============**/
        Transactions transaction  = gson.fromJson(TransactionRequest,Transactions.class);
        TransactionOperations transactionOperations = new TransactionOperations();
        try{
            List<Issued_Device>issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transaction.getAgent_id());
            if(issued_devices.size()>0){
                issued_devices.forEach((x) ->{
                    issued_device = x;
                    logger.info("**issued device**");
                    logger.info(x.getString());
                    device = deviceRepository.findDeviceById(issued_device.getDeviceId());
                    logger.info("issued device");
                    logger.info(device.getString());
                });
            }
            else{
                responseMessage.setMessage(String.format("DEVICE ISSUANCE ERROR.Device %s not issued to agent",device.getMacAddress()));
                //logger.info(responseMessage.getMessage());
                apiResponse.setMessage(responseMessage.getMessage());
                apiResponse.setCode(301);
                logger.info(gson.toJson(apiResponse));
                //return ResponseEntity.status(403).body(responseMessage.getMessage());
                return ResponseEntity.status(201).body(apiResponse);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            apiResponse.setCode(153);
            apiResponse.setMessage(gson.toJson(TransactionRequest));
            //return ResponseEntity.status(500).body(TransactionRequest);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
                    //ACTION POINTS
        /*
        -Generate customer from transactions String
        -validate authentication
        -validate device
        -validate agent
        -validate account
        -authenticate customer
        */

                    /**********************SKIP IMPORTANT ACTION POINS HERE. Revert once details rae available***********************************************/
        //Account requestedAccount = accountsController.findActiveAccountByAccountNumber(transaction.getAccount_from()) != null ? accountsController.findActiveAccountByAccountNumber(transaction.getAccount_from()) : fallBackAccount;
        //Customer transactingCustomer = customerController.findActiveCustomerByIdNumber(requestedAccount.getCustomer_id_number())!=null ? customerController.findActiveCustomerByIdNumber(requestedAccount.getCustomer_id_number()):fallBackCustomer;
        if(!validateTransactions.authenticateAgent(transaction.getAgent_id())){
            String message = String.format("Agent %d  is INACTIVE or NOT registered", agentRepository.findTransactingAgentById(transaction.getAgent_id()).getAgent_description());
            responseMessage.setMessage(message);
            //logger.info(responseMessage.getMessage());
            apiResponse.setCode(300);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id())){
            String message = String.format("Device  = %d  is not issued to agent %s ",issued_device.getDeviceId(),agentRepository.findActiveAgentDescritpionById(issued_device.getAgent_id()));
            responseMessage.setMessage(message);
            //logger.info(responseMessage.getMessage());
            apiResponse.setCode(301);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            //return ResponseEntity.status(201).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(apiResponse);

        }
        if(!validateTransactions.authenticateDevice(device.getId())){
            String message = String.format("Device %s is not registered or is inactive",device.getMacAddress());
            responseMessage.setMessage(message);
            apiResponse.setMessage(message);
            apiResponse.setCode(302);
            logger.info(gson.toJson(apiResponse));
            //logger.info(responseMessage.getMessage());
            //return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
            return ResponseEntity.status(201).body(gson.toJson(apiResponse));
        }
                    /**SKIP THIS FOR NOW, NO ACCOUNT DETAILS***/
/*        if(!validateTransactions.authenticateAccount(transaction.getAccount_from())){
            String message = String.format("Account  %s is not registered or is inactive",transaction.getAccount_from());
            responseMessage.setMessage(message);
            logger.info(responseMessage.getMessage());
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }*/
/*        if(!validateTransactions.authenticateCustomer(transactingCustomer)){
            String message = String.format("Customer  %s is not registered or is inactive",transactingCustomer.getFirst_name());
            responseMessage.setMessage(message);
            logger.info(responseMessage.getMessage());
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }*/
                //GENERATE RECEIPT NUMBER AND SET NARRATION TYPE
        transaction.setNarration(transactionOperationsRepository.findTransaction_OperationById(transaction.getOperational_id()).getAction());
        transaction.setReceipt_number(receiptManager.generateReceiptNumber(transaction.getAgent_id()));
                /**===============MAP TRANSACTION TO INQUIRIES REQUEST AND SEND TO COMPAS BRIDGE  ==================*******/
        InquiriesRequestData inquiriesRequestData = mapTransactionsToInquiriesRequest(transaction);
                /**================SEND TRANSACTIONS TO COMPAS BRIDGE =================================**/
        String responseData = restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(transaction,API_USERNAME),transaction.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(transaction.getOperational_id()));
        logger.info("******"+responseData);
        logger.info("PASSING TO OperationEnums");
       classifyFinnacleBridgeResponse(responseData);
        logger.info("<<<<<>>>>>");
        logger.info(mapFinnacleBridgeResponseToCompasResponse(classifyFinnacleBridgeResponse(responseData),responseData));
        apiResponse = gson.fromJson(responseData,ApiResponse.class);
        logger.info(apiResponse.getMessage());
        logger.info("*****-----******");
        //apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/thirdparty/PbuThirdPartyAcess.aspx","POST",transactionsToBank.prepareTransactionsToBank(transaction,API_USERNAME),transaction.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(transaction.getOperational_i)d()),ApiResponse.class);
        if(apiResponse.getCode() != 201){
            responseMessage.setMessage(String.format("%s INQUIRY WITH TRANSID %s FAILED",inquiriesRequestData.getNarration().toUpperCase(),inquiriesRequestData.getTransId()));
            //logger.info(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
            //return ResponseEntity.status(400).body(gson.toJson(responseMessage.getMessage()));
        }

        /**===========================PROCESS API RESPONSE=======================================*/
        apiResponse = ProcessCBSResponse(inquiriesRequestData.getTransId(),apiResponse,"  INQUIRY");
                /**===============UPDATE inquiries request data status to 'S' ============================**/
/*                logger.info("*********TRANSID[%s]***************************",inquiriesRequestData.getTransId());
               // logger.info(inquiriesRequestData.getTransId());
        inquiriesRequestDataRepository.updateSuccessfulInquiryRequestData(inquiriesRequestData.getTransId(),apiResponse.getData().getTransId());
        apiResponse.setCode(150);
        //apiResponse.setMessage(gson.fromJson(responseData,ApiResponse.class).getMessage().replaceAll("\\\\",""));
        logger.info(gson.toJson(apiResponse));*/
        return ResponseEntity.status(201).body(apiResponse);

    }

    public InquiriesRequestData mapTransactionsToInquiriesRequest(Transactions transaction){
        TransactionOperations transactionOperations = new TransactionOperations();
        InquiriesRequestData inquiriesRequestData = new InquiriesRequestData();
        inquiriesRequestData.setTransId(transaction.getReceipt_number());
        inquiriesRequestData.setNarration(transaction.getNarration());
        inquiriesRequestData.setAccount(transaction.getAccount_from());
        inquiriesRequestData.setDeviceId(transaction.getMacaddress());
        inquiriesRequestData.setTellerId(transaction.getAgent_id().toString());
        inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()));
        inquiriesRequestData.setStatus("I");
        //inquiriesRequestData.setCustomerName(accountsController.findActiveAccountByAccountNumber(transaction.account_from).getAccount_name());
                    /***===========SAVE TO INQUIRIES REQUEST TABLE======================*/
        inquiriesRequestDataRepository.save(inquiriesRequestData);
        return  inquiriesRequestData;
    }

    public OperationEnums  classifyFinnacleBridgeResponse(String finnacleResponse){
                logger.info("Init...");
        if(gson.fromJson(finnacleResponse,WithdrawalResponse.class)!=null){
            logger.info(opsenums.WITHDRAWAL.toString());
            return opsenums.WITHDRAWAL;
        }
        else if(gson.fromJson(finnacleResponse,DepositResponse.class) instanceof DepositResponse){
            logger.info(opsenums.DEPOSIT.toString());
            return opsenums.DEPOSIT;
        }
        else if(gson.fromJson(finnacleResponse,TransferResponse.class) instanceof TransferResponse){
            logger.info(opsenums.TRANSFER.toString());
            return opsenums.TRANSFER;
        }
        else if(gson.fromJson(finnacleResponse,InquiriesResponse.class) instanceof InquiriesResponse){
            logger.info(opsenums.INQUIRIES.toString());
            return opsenums.INQUIRIES;
        }
        else if(gson.fromJson(finnacleResponse,ReversalResponse.class) instanceof ReversalResponse){
            logger.info(opsenums.REVERSAL.toString());
            return opsenums.REVERSAL;
        }
        else if(gson.fromJson(finnacleResponse, AccountInquiryResponse.class) instanceof AccountInquiryResponse){
            logger.info(opsenums.ACCOUNT_INQUIRY.toString());
            return opsenums.ACCOUNT_INQUIRY;
        }
        return opsenums.DEFAULT;
    }

    public String mapFinnacleBridgeResponseToCompasResponse(OperationEnums operationEnums,String finnacleResponse){
        switch (operationEnums){
            case DEFAULT:
                return "";
            case DEPOSIT:
                return gson.toJson(gson.fromJson(finnacleResponse,DepositResponse.class));
            case REVERSAL:
                return gson.toJson(gson.fromJson(finnacleResponse,ReversalResponse.class));
            case TRANSFER:
                return gson.toJson(gson.fromJson(finnacleResponse,TransferResponse.class));
            case INQUIRIES:
                return gson.toJson(gson.fromJson(finnacleResponse,InquiriesResponse.class));
            case WITHDRAWAL:
                return gson.toJson(gson.fromJson(finnacleResponse,WithdrawalResponse.class));
            case ACCOUNT_INQUIRY:
                return gson.toJson(gson.fromJson(finnacleResponse,AccountInquiryResponse.class));
        }
        return "";
    }

    public ApiResponse ProcessCBSResponse(String transId,ApiResponse apiResponse,String intent){
            Boolean isInquiry;
            isInquiry = intent!=null;
        switch(apiResponse.getResponse_code()){
            case "000":
                if(isInquiry){inquiriesRequestDataRepository.updateSuccessfulInquiryRequestData(transId,apiResponse.getData().getTransId());}
                transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(transId,apiResponse.getData().getTransId());
                apiResponse.setCode(150);
                logger.info(gson.toJson(apiResponse));
                break;
            case "303":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(153);
                apiResponse.setMessage("General error");
                logger.info(gson.toJson(apiResponse));
                break;
            case "155":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(154);
                apiResponse.setMessage("INTEGRITY FAILURE");
                logger.info(gson.toJson(apiResponse));
                break;
            case "404":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(155);
                apiResponse.setMessage("UNKNOWN TRANSACTION REQUEST");
                logger.info(gson.toJson(apiResponse));
                break;
            case "114":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(156);
                apiResponse.setMessage("INSUFFICIENT FUNDS");
                logger.info(gson.toJson(apiResponse));
                break;
            case "911":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(157);
                apiResponse.setMessage("TRANSACTION TIME OUT");
                logger.info(gson.toJson(apiResponse));
                break;
            case "154":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(158);
                apiResponse.setMessage("TRANSACTION NOT FOUND");
                logger.info(gson.toJson(apiResponse));
                break;
            case "156":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(159);
                apiResponse.setMessage("MALFORMED TRANSACTION");
                logger.info(gson.toJson(apiResponse));
                break;
            case "157":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(160);
                apiResponse.setMessage("TRANSACTION FAILED");
                logger.info(gson.toJson(apiResponse));
                break;
            case "149":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(161);
                apiResponse.setMessage("DUPLICATE TRANSACTION ID");
                logger.info(gson.toJson(apiResponse));
                break;
            case "200":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId);}
                transactionRDBMSRepository.updateFailedTransactions(transId);
                apiResponse.setCode(162);
                apiResponse.setMessage("PENDING TRANSACTION");
                logger.info(gson.toJson(apiResponse));
                break;

        }

        return apiResponse;
    }

}


