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
import compas.models.bankoperations.AccountInquiry.AccountInquiryRequest;
import compas.models.bankoperations.AccountInquiry.AccountInquiryResponse;
import compas.models.bankoperations.Inquiries.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        logger.info(String.format("TRANSACTION REQUEST[:=>"+transactionString+"<=:]"));
        Transactions transactions = gson.fromJson(transactionString,Transactions.class);
        /***SET TRANSACTION DATE HERE*********/
        transactions.setTransaction_date(LocalDateTime.now().toString());
        logger.info(gson.toJson(transactions));
        /***VALIDATE TRANSACTIONS PAYLOAD ***/
        String buildTransactionValidityString = validateTransactionPayload(transactions);
        if(buildTransactionValidityString.trim().length() !=0){
            /** this means the transaction payload is invalid**/
            ApiResponse invalidResponse = buildInvalidTransactionResponse(buildTransactionValidityString.trim());
            return ResponseEntity.status(201).body(gson.toJson(invalidResponse));
        }
        Device wildDevice = new Device();
        try{
            wildDevice.setId(0);
            List<Issued_Device>issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transactions.getAgent_id());
            logger.info("Issued devices::"+issued_devices.size());
            if(issued_devices.size()>0){
                issued_devices.forEach((x) ->{
                    issued_device = x;
                    logger.info(x.getString());
                    device = deviceRepository.findDeviceById(issued_device.getDeviceId())!=null ? deviceRepository.findDeviceById(issued_device.getDeviceId()) :wildDevice;
                    logger.info(device.getString());

                });
            }
            else{
                apiResponse.setCode(301);
                apiResponse.setMessage("DEVICE IS  NOT ISSUED TO AGENT");
                logger.info(gson.toJson(apiResponse));
                return ResponseEntity.status(201).body(apiResponse);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(transactions);
        }


        //FIRST CHECKPOINT>>>>>>VALIDATE TRANSACTION
        if(!validateTransactions.authenticateAgent(transactions.getAgent_id())){
            String message = String.format("Agent %d  is INACTIVE or NOT registered", transactions.getAgent_id());
            responseMessage.setMessage(message);
            apiResponse.setCode(300);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id())){
            String message = String.format("Device  = %s  is not issued to agent %d ",issued_device.getDeviceId(),issued_device.getAgent_id());
            responseMessage.setMessage(message);
            apiResponse.setCode(301);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);

        }
        if(!validateTransactions.authenticateDevice(device.getId())){
            String message = String.format("Device %s is not registered  or is inactive",device.getMacAddress());
            responseMessage.setMessage(message);
            apiResponse.setCode(302);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }

        if(!validateTransactions.validateDailyTransactionLimit(transactions)){
            responseMessage.setMessage(String.format("Daily Transaction limit has been attained"));
            logger.info(responseMessage.getMessage());
            apiResponse.setCode(303);
            apiResponse.setMessage(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateWeeklyTransactionLimits(transactions)){
            responseMessage.setMessage(String.format("Weekly transaction limit has been attained",transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction()));
            logger.info(responseMessage.getMessage());
            apiResponse.setCode(308);
            apiResponse.setMessage(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateMonthlyTransactionLimits(transactions)){
            responseMessage.setMessage(String.format("Monthly transaction limit has been attained",transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction()));
            logger.info(responseMessage.getMessage());
            apiResponse.setCode(309);
            apiResponse.setMessage(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateQuarterlyTransactionLimits(transactions)){
            responseMessage.setMessage(String.format("Quarterly transaction limit has been attained",transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction()));
            logger.info(responseMessage.getMessage());
            apiResponse.setCode(310);
            apiResponse.setMessage(responseMessage.getMessage());
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!(validateTransactions.authenticateAccount(transactions.getAccount_from())&& validateTransactions.authenticateAccount(transactions.getAccount_to()))){
            apiResponse.setMessage("Invalid account number");
            apiResponse.setCode(304);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);

        }
        //GENERATE receipt_number
        receipt_number = receiptManager.generateReceiptNumber(transactions.getAgent_id());
        //SET receipt number and narration
        transactions.setReceipt_number(receipt_number);
        transactions.setNarration(transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id()).getAction());
        transactions.setTransaction_type_id(transactionOperationsRepository.findTransactionTypeIdByTransactionOperationId(transactions.getOperational_id()));
        //SET customer name for transaction
        ApiResponse accountInquiryResponse = gson.fromJson(performAcctInquiry(gson.toJson(transactions)),ApiResponse.class);
        if(accountInquiryResponse.getData()!=null){
            transactions.setCustomer_name(accountInquiryResponse.getData().getAcctName());
        }
        else{
            //proceed with transaction
            transactions.setCustomer_name("");
        }
        //CHECK TRANSACTION AUTH MODE
        String transactionMode = authenticationModeRepository.findAuth_ModeById(transactions.getAuth_mode()).getMode();
        logger.info("AUTHENTICATION:::"+transactionMode);
        if(transactionMode.equalsIgnoreCase("OTP")){
            //NOW GENERATE OTP and send to CBS
            String response = otpController.generateOTPPassword(transactions,receipt_number);
            smsResponse = gson.fromJson(response,SmsResponse.class);
            /***ARREST MARTIN HERE******/
            if(smsResponse.getResponse_code() == null && !smsResponse.getResponse_code().equals("000")/*201*/){
                smsResponse.setCode(100);
                smsResponse.setResponse_message("OTP MESSAGE SERVER FAILED");
                logger.info(gson.toJson(smsResponse));
                return ResponseEntity.status(201).body(smsResponse);
            }
            if(!smsResponse.getResponse_code().equals("000")){
                smsResponse.setResponse_message("Phone number not found for account");
                logger.info(gson.toJson(smsResponse));
                return ResponseEntity.status(201).body(smsResponse);
            }
            //update flag to "P"
            transactions.setStatus("P");
            /***calculate tariffs and get processed transactions***/
            //Transactions processedTransactions = tariffManager.setTariffCharges(transactions);
            Transactions processedTransactions = tariffManager.setCharges(transactions);
            transactionRepository.saveTransaction(processedTransactions);
            apiResponse.setCode(101);
            apiResponse.setMessage("OTP GENERATION SUCCESS");
            return ResponseEntity.status(201).body(apiResponse);

        }
        else if(transactionMode.equalsIgnoreCase("PIN")){
            transactions.setStatus("I");
            Transactions processedTransactions = tariffManager.setCharges(transactions);
            ///AUTHENTICATE TRANSACTION AT THIS POINT
            if(!validateTransactions.processAuthentication(transactions.getAuth_mode(),transactions.getAuthentication(),transactions.getAccount_from())){
                responseMessage.setMessage(String.format("PIN AUTHENTICATION FAILED FOR TRANSACTION %s FROM AGENT %s FOR ACCOUNT %s",transactions.getNarration(),agentRepository.findTransactingAgentById(transactions.getAgent_id()).getAgent_description(),transactions.getAccount_from()));
                apiResponse.setCode(305);
                apiResponse.setMessage(responseMessage.getMessage());
                logger.info(String.format("TRANSACTION RESPONSE{"+apiResponse.getString()+"}"));
                return ResponseEntity.status(201).body(apiResponse);
            }
            transactionRepository.saveTransaction(processedTransactions);
            transactionRDBMSRepository.save(processedTransactions);

            /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
            String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            logger.info(responseData);
            apiResponse = gson.fromJson(responseData,ApiResponse.class);
            if(apiResponse.getCode() == 201){
                apiResponse = ProcessCBSResponse(processedTransactions.getReceipt_number(),apiResponse,"");
                if(apiResponse.getData()!=null) {
                    apiResponse.getData().setAcctName(processedTransactions.getCustomer_name());
                    apiResponse.getData().setReceipt_number((processedTransactions.getReceipt_number()));
                    apiResponse.getData().setAgent_commission(processedTransactions.getAgent_commision());
                    apiResponse.getData().setBank_income(processedTransactions.getBank_income());
                    apiResponse.getData().setExercise_duty(processedTransactions.getExcise_duty());
                }
                //logger.info(String.format("TRANSACTION RESPONSE{"+apiResponse.getString()+"}"));
                logger.info(String.format("TRANSACTION RESPONSE{"+gson.toJson(apiResponse)+"}"));
                return ResponseEntity.status(201).body(apiResponse);
            }
            /***UPDATE TRANSACTION AS FAILED TRANSACTION*/
            transactionRDBMSRepository.updateFailedTransactions(processedTransactions.getReceipt_number(),apiResponse.getResponse_message());
            logger.info(String.format("TRANSACTION RESPONSE{"+gson.toJson(apiResponse)+"}"));
            return ResponseEntity.status(201).body(apiResponse);

        }
        else if(transactionMode.equalsIgnoreCase("BIO")){
            transactions.setStatus("I");
            Transactions processedTransactions = tariffManager.setCharges(transactions);
            //AUTHENTICATE TRANSACTION AT THIS POINT
            if(!validateTransactions.processAuthentication(transactions.getAuth_mode(),transactions.getAuthentication(),transactions.getAccount_from())){
                responseMessage.setMessage(String.format("BIO AUTHENTICATION FAILED FOR TRANSACTION %s FROM AGENT %s FOR ACCOUNT %s",transactions.getNarration(),agentRepository.findTransactingAgentById(transactions.getAgent_id()).getAgent_description(),transactions.getAccount_from()));
                logger.info(responseMessage.getMessage());
                apiResponse.setCode(306);
                apiResponse.setMessage(responseMessage.getMessage());
                return ResponseEntity.status(201).body(apiResponse);
            }
            transactionRepository.saveTransaction(processedTransactions);
            transactionRDBMSRepository.save(processedTransactions);

            /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
            String responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
            logger.info(responseData);
            apiResponse = gson.fromJson(responseData,ApiResponse.class);
            if(apiResponse.getCode() ==201){
                apiResponse = ProcessCBSResponse(processedTransactions.getReceipt_number(),apiResponse,"");
                if(apiResponse.getData()!=null) {
                    apiResponse.getData().setAcctName(processedTransactions.getCustomer_name());
                    apiResponse.getData().setReceipt_number(processedTransactions.getReceipt_number());
                    apiResponse.getData().setAgent_commission(processedTransactions.getAgent_commision());
                    apiResponse.getData().setBank_income((processedTransactions.getBank_income()));
                    apiResponse.getData().setExercise_duty(processedTransactions.getExcise_duty());
                }
                logger.info(String.format("TRANSACTION RESPONSE{"+gson.toJson(apiResponse)+"}"));
                return ResponseEntity.status(201).body(apiResponse);

            }
            else{
                logger.info(String.format("TRANSACTION RESPONSE{"+gson.toJson(apiResponse)+"}"));
                transactionRDBMSRepository.updateFailedTransactions(processedTransactions.getReceipt_number(),apiResponse.getResponse_message());
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));
            }
        }
        else{
            //transactions needing no AUTHENTICATION i.e cash deposits and Inquiries
            Transactions processedTransactions = tariffManager.setCharges(transactions);
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
                if(apiResponse.getData()!=null) {
                    apiResponse.getData().setAcctName(processedTransactions.getCustomer_name());
                    apiResponse.getData().setReceipt_number(processedTransactions.getReceipt_number());
                    apiResponse.getData().setAgent_commission(processedTransactions.getAgent_commision());
                    apiResponse.getData().setBank_income(processedTransactions.getBank_income());
                    apiResponse.getData().setExercise_duty(processedTransactions.getExcise_duty());
                }
                //logger.info(String.format("TRANSACTION RESPONSE{"+apiResponse.getString()+"}"));
                logger.info("TRANSACTION RESPONSE ["+gson.toJson(apiResponse)+"]");
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));

            }
            else{
                logger.info("TRANSACTION RESPONSE{"+gson.toJson(apiResponse)+"}");
                transactionRDBMSRepository.updateFailedTransactions(processedTransactions.getReceipt_number(),apiResponse.getResponse_message());
                return ResponseEntity.status(201).body(gson.toJson(apiResponse));
            }

        }
    }

    @Transactional
    @RequestMapping(path="/inquiries",method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity postInquiries(@RequestBody String TransactionRequest){
        logger.info(String.format("INQUIRIES REQUEST["+TransactionRequest+"]"));
        /****===============Model inquiries request data from Transaction Payload==============**/
        Transactions transaction  = gson.fromJson(TransactionRequest,Transactions.class);
        TransactionOperations transactionOperations = new TransactionOperations();
        /***SET TRANSACTION DATE HERE*********/
        transaction.setTransaction_date(LocalDateTime.now().toString());
        try{
            List<Issued_Device>issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transaction.getAgent_id());
            if(issued_devices.size()>0){
                issued_devices.forEach((x) ->{
                    issued_device = x;
                    logger.info(x.getString());
                    device = deviceRepository.findDeviceById(issued_device.getDeviceId());
                    logger.info(device.getString());
                });
            }
            else{
                responseMessage.setMessage(String.format("DEVICE ISSUANCE ERROR.Device %s not issued to agent",device.getMacAddress()));
                apiResponse.setMessage(responseMessage.getMessage());
                apiResponse.setCode(301);
                logger.info(gson.toJson(apiResponse));
                return ResponseEntity.status(201).body(apiResponse);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            apiResponse.setCode(153);
            apiResponse.setMessage(gson.toJson(TransactionRequest));
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

        /**********************SKIP IMPORTANT ACTION POINTS HERE. Revert once details are available***********************************************/
        if(!validateTransactions.authenticateAgent(transaction.getAgent_id())){
            String message = String.format("Agent1 %d  is INACTIVE or NOT registered", agentRepository.findTransactingAgentById(transaction.getAgent_id()).getAgent_description());
            responseMessage.setMessage(message);
            apiResponse.setCode(300);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        if(!validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id())){
            String message = String.format("Device  = %d  is not issued to agent %s ",issued_device.getDeviceId(),agentRepository.findActiveAgentDescritpionById(issued_device.getAgent_id()));
            responseMessage.setMessage(message);
            apiResponse.setCode(301);
            apiResponse.setMessage(message);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);

        }
        if(!validateTransactions.authenticateDevice(device.getId())){
            String message = String.format("Device %s is not registered or is inactive",device.getMacAddress());
            responseMessage.setMessage(message);
            apiResponse.setMessage(message);
            apiResponse.setCode(302);
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(gson.toJson(apiResponse));
        }

        /**GENERATE RECEIPT NUMBER AND SET NARRATION TYPE**/
        transaction.setNarration(transactionOperationsRepository.findTransaction_OperationById(transaction.getOperational_id()).getAction());
        receipt_number = receiptManager.generateReceiptNumber(transaction.getAgent_id());
        transaction.setReceipt_number(receipt_number);
        transaction.setTransaction_type_id(transactionOperationsRepository.findTransactionTypeIdByTransactionOperationId(transaction.getOperational_id()));

        /**SET TRANSACTION CHARGES HERE *****/
        Transactions processedTransactions = tariffManager.setCharges(transaction);

        /***CHECK AUTHENTICATION HERE **/
        if(processedTransactions.auth_mode == null){
            apiResponse.setCode(314);
            apiResponse.setMessage("TRANSACTION IS NOT VALIDLY AUTHENTICATED");
            return ResponseEntity.status(201).body(gson.toJson(apiResponse));
        }
        String transactionMode = authenticationModeRepository.findAuth_ModeById(processedTransactions.getAuth_mode()).getMode();

        if(transactionMode.equalsIgnoreCase("OTP")){
            String response = otpController.generateOTPPassword(processedTransactions,receipt_number);
            smsResponse = gson.fromJson(response,SmsResponse.class);
            /***ARREST MARTIN HERE******/
            if(smsResponse.getResponse_code() == null && !smsResponse.getResponse_code().equals("000")/*201*/){

                smsResponse.setCode(100);
                smsResponse.setResponse_message("OTP MESSAGE SERVER FAILEDD");
                logger.info(gson.toJson(smsResponse));
                return ResponseEntity.status(201).body(smsResponse);
            }
            if(!smsResponse.getResponse_code().equals("000")){
                smsResponse.setResponse_message("Phone number not found for account");
                logger.info(gson.toJson(smsResponse));
                return ResponseEntity.status(201).body(smsResponse);
            }

            processedTransactions.setStatus("P");
            transactionRepository.saveTransaction(processedTransactions);
            apiResponse.setCode(101);
            apiResponse.setMessage("OTP GENERATION SUCCESS");
            return ResponseEntity.status(201).body(apiResponse);
        }
        else if(transactionMode.equalsIgnoreCase("PIN")){
            logger.info("PIN authentication");
            apiResponse = processAuthenticatedInquiry(processedTransactions);
            if(apiResponse.Data!=null) {
                apiResponse.getData().setExercise_duty(processedTransactions.getExcise_duty());
                apiResponse.getData().setBank_income(processedTransactions.getBank_income());
                apiResponse.getData().setAgent_commission(processedTransactions.getAgent_commision());
            }
            return ResponseEntity.status(201).body(apiResponse);

        }
        else if(transactionMode.equalsIgnoreCase("BIO")){
            apiResponse = processAuthenticatedInquiry(processedTransactions);
            if(apiResponse.Data!=null) {
                apiResponse.getData().setExercise_duty(processedTransactions.getExcise_duty());
                apiResponse.getData().setBank_income(processedTransactions.getBank_income());
                apiResponse.getData().setAgent_commission(processedTransactions.getAgent_commision());
            }
            return ResponseEntity.status(201).body(apiResponse);
        }
        else{
            //invalid authentication mode
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(314);
            apiResponse.setMessage("TRANSACTION IS NOT VALIDLY AUTHENTICATED");
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(314);
        apiResponse.setMessage("TRANSACTION IS NOT VALIDLY AUTHENTICATED");
        return ResponseEntity.status(201).body(apiResponse);
    }

    public ApiResponse processAuthenticatedInquiry(Transactions processedTransactions){
        InquiriesRequestData inquiriesRequestData = mapTransactionsToInquiriesRequest(processedTransactions);
        /**================SEND TRANSACTIONS TO COMPAS BRIDGE =================================**/
        String responseData = restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(processedTransactions,API_USERNAME),processedTransactions.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(processedTransactions.getOperational_id()));
        logger.info("******"+responseData);
        apiResponse = gson.fromJson(responseData,ApiResponse.class);
        if(apiResponse.getCode() != 201){
            responseMessage.setMessage(String.format("%s INQUIRY WITH TRANSID %s FAILED",inquiriesRequestData.getNarration().toUpperCase(),inquiriesRequestData.getTransId()));
            return apiResponse;
        }
        if(apiResponse.Data!=null){
            //apiResponse.getData().setAcctName(processedTransactions.getCustomer_name());
            apiResponse.getData().setAcctName(inquiriesRequestData.getCustomerName());
        }

        /**===========================PROCESS API RESPONSE=======================================*/
        apiResponse = ProcessCBSResponse(inquiriesRequestData.getTransId(),apiResponse,"  INQUIRY");
        logger.info("INQUIRY RESPONSE{"+gson.toJson(apiResponse)+"}");
        return apiResponse;
    }

    public InquiriesRequestData mapTransactionsToInquiriesRequest(Transactions transaction){
        TransactionOperations transactionOperations = new TransactionOperations();
        Transaction_Operation transaction_operation = transactionOperationsRepository.findTransaction_OperationById(transaction.getOperational_id());
        InquiriesRequestData inquiriesRequestData = new InquiriesRequestData();
        inquiriesRequestData.setTransId(transaction.getReceipt_number());
        inquiriesRequestData.setNarration(transaction.getNarration());
        inquiriesRequestData.setDeviceId(transaction.getMacaddress());
        inquiriesRequestData.setTellerId(transaction.getAgent_id().toString());
        inquiriesRequestData.setCustomerName(transaction.getCustomer_name());
        inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
        inquiriesRequestData.setStatus("I");
        //perform account inquiry on account number
        if(transaction_operation.getAction().toUpperCase().equals("BAL_LIST")){
            inquiriesRequestData.setAccountsList(transaction.getAccounts_list());
        }
        else {
            logger.info(">>>>>>>>>>");
            inquiriesRequestData.setAccount(transaction.getAccount_from());
            ApiResponse accountInquiryResponse = gson.fromJson(performAcctInquiry(gson.toJson(transaction)), ApiResponse.class);
            logger.info(gson.toJson(accountInquiryResponse));
            if (apiResponse.getData() == null) {
                //proceed with transaction
                inquiriesRequestData.setCustomerName("OKURA RONALD");
            }
            else if(apiResponse.Data!=null){
                logger.info("check....");
                logger.info("account name::"+accountInquiryResponse.getData().getAcctName());
                inquiriesRequestData.setCustomerName(accountInquiryResponse.getData().getAcctName());
            }
        }
        /***===========SAVE TO INQUIRIES REQUEST TABLE======================*/
        inquiriesRequestDataRepository.save(inquiriesRequestData);
        return  inquiriesRequestData;
    }

    public Transactions mapInquiriesRequestToTransaction(InquiriesRequestData inquiriesRequestData){
        Transactions transaction = new Transactions();
        transaction.setAccount_from(inquiriesRequestData.getAccount());
        transaction.setCbs_trans_id(inquiriesRequestData.getCbs_trans_id());
        transaction.setReceipt_number(inquiriesRequestData.getTransId());
        transaction.setAmount((Integer.parseInt(inquiriesRequestData.getCharges()[0]))+(Integer.parseInt(inquiriesRequestData.getCharges()[1]))+(Integer.parseInt(inquiriesRequestData.getCharges()[2])));
        transaction.setAgent_id(Integer.parseInt(inquiriesRequestData.getTellerId()));
        transaction.setCustomer_name(inquiriesRequestData.getCustomerName());
        transaction.setOperational_id(transactionOperationsRepository.findTransaction_OperationIdByAction(inquiriesRequestData.getNarration()));
        return transaction;
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
                if(isInquiry){inquiriesRequestDataRepository.updateSuccessfulInquiryRequestData(transId,apiResponse.getData().getTransId(),apiResponse.getResponse_message());}
                transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(transId,apiResponse.getData().getTransId(),apiResponse.getResponse_message());
                apiResponse.setCode(150);
                apiResponse.getData().setReceipt_number(transId);
                logger.info(gson.toJson(apiResponse));
                break;
            case "303":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message() !=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(153);
                apiResponse.setMessage("General error");
                logger.info(gson.toJson(apiResponse));
                break;
            case "155":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message() :""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null ? apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(154);
                apiResponse.setMessage("INTEGRITY FAILURE");
                logger.info(gson.toJson(apiResponse));
                break;
            case "404":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(155);
                apiResponse.setMessage("UNKNOWN TRANSACTION REQUEST");
                logger.info(gson.toJson(apiResponse));
                break;
            case "114":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null ? apiResponse.getResponse_message() :""+apiResponse.getResponse_status());
                apiResponse.setCode(156);
                apiResponse.setMessage("INSUFFICIENT FUNDS");
                logger.info(gson.toJson(apiResponse));
                break;
            case "911":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null ?apiResponse.getResponse_message() :""+apiResponse.getResponse_status());
                apiResponse.setCode(157);
                apiResponse.setMessage("TRANSACTION TIME OUT");
                logger.info(gson.toJson(apiResponse));
                break;
            case "154":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(158);
                apiResponse.setMessage("TRANSACTION NOT FOUND");
                logger.info(gson.toJson(apiResponse));
                break;
            case "156":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(159);
                apiResponse.setMessage("MALFORMED TRANSACTION");
                logger.info(gson.toJson(apiResponse));
                break;
            case "157":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(160);
                apiResponse.setMessage("TRANSACTION FAILED");
                logger.info(gson.toJson(apiResponse));
                break;
            case "149":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(161);
                apiResponse.setMessage("DUPLICATE TRANSACTION ID");
                logger.info(gson.toJson(apiResponse));
                break;
            case "200":
                if(isInquiry){inquiriesRequestDataRepository.updateFailedInquiryRequestData(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());}
                transactionRDBMSRepository.updateFailedTransactions(transId,apiResponse.getResponse_message()!=null?apiResponse.getResponse_message():""+apiResponse.getResponse_status());
                apiResponse.setCode(162);
                apiResponse.setMessage("PENDING TRANSACTION");
                logger.info(gson.toJson(apiResponse));
                break;

        }

        return apiResponse;
    }

    public String validateTransactionPayload(Transactions transaction){
        List<Transactions> transactions = new ArrayList<>();
        transactions.add(transaction);
        String evaluation ="" ;
        Transactions txn = transactions.get(0);
        if(txn.getAmount() ==null){
            logger.info(("INVALID TRANSACTION PAYLOAD. AMOUNT MISSING"));
            evaluation = evaluation+"1";
        }
        if(txn.getAgent_id() == null){
            logger.info(("INVALID TRANSACTION PAYLOAD. AGENT ID MISSING"));
            evaluation = evaluation+"2";
        }
        if(txn.getAuth_mode() ==null){
            logger.info(("INVALID TRANSACTION PAYLOAD. AUTH MODE MISSING"));
            evaluation = evaluation+"3";
        }
        if(txn.getMode_id() == null){
            logger.info(("INVALID TRANSACTION PAYLOAD. TRANSACTION MODE MISSING"));
            evaluation = evaluation+"4";
        }
        if(txn.getOperational_id() == null){
            logger.info(("INVALID TRANSACTION PAYLOAD. OPERATION  ID MISSING"));
            evaluation = evaluation+"5";
        }
        if(txn.getCurrency_id() == null){
            logger.info(("INVALID TRANSACTION PAYLOAD. CURRENCY ID MISSING"));
            evaluation = evaluation+"6";
        }
        if(txn.getAccount_from() == null) {
            logger.info(("INVALID TRANSACTION PAYLOAD. ACCOUNT FROM MISSING"));
            evaluation = evaluation+"7";
        }
        if(txn.getAccount_to() == null){
            logger.info(("INVALID TRANSACTION PAYLOAD. ACCOUNT TO MISSING"));
            evaluation = evaluation+"8";
        }

        logger.info(evaluation);
        return evaluation;
    }

    public ApiResponse buildInvalidTransactionResponse(String invalidityString){
        ApiResponse invalidResponse = new ApiResponse();
        invalidResponse.setCode(307);
        String responseMessage = "INVALID TRANSACTION PAYLOAD;";
        char [] stringBuffer = invalidityString.toCharArray();
        for(char c :stringBuffer){
            if(c == '1'){
                responseMessage = responseMessage+". AMOUNT MISSING.";
            }
            else if(c=='2'){
                responseMessage = responseMessage+("AGENT ID MISSING.");

            }
            else if(c=='3'){
                responseMessage = responseMessage+("AUTH MODE MISSING.");
            }
            else if(c=='4'){
                responseMessage = responseMessage+("TRANSACTION MODE MISSING.");

            }
            else if(c=='5'){
                responseMessage = responseMessage+("OPERATION NOT SUPPORTED.");
            }
            else if(c=='6'){
                responseMessage = responseMessage+("CURRENCY ID MISSING.");
            }
            else if(c=='7'){
                responseMessage = responseMessage+("ACCOUNT FROM MISSING.");
            }
            else if(c=='8'){
                responseMessage = responseMessage+"ACCOUNT TO MISSING.";
            }
        }

        invalidResponse.setMessage(responseMessage);
        logger.info(gson.toJson(invalidResponse));
        logger.info(responseMessage);
        return invalidResponse;
    }


    public String  performAcctInquiry( String incomingtransaction){
        Transactions transaction = gson.fromJson(incomingtransaction,Transactions.class);
        Transactions originalTransaction = gson.fromJson(incomingtransaction,Transactions.class);
        transaction.setOperational_id(transactionOperationsRepository.findTransaction_OperationIdByAction("ACCT_INQUIRY"));
        transaction.setBank_income(0.0);
        transaction.setAgent_commision(0.0);
        transaction.setExcise_duty(0.0);
        transaction.setReceipt_number(LocalDateTime.now().toString().replace(":","").replace(".","").replace("-",""));
        String responseData = restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(transaction,originalTransaction,API_USERNAME),transaction.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(transaction.getOperational_id()));
        return responseData;
    }

    @ResponseBody
    @RequestMapping(path = "/confirm_account",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity confirmTransactingAccount(@RequestBody String transaction){
        ApiResponse account_inquiry_response = gson.fromJson(performAcctInquiry(transaction),ApiResponse.class);
        if(account_inquiry_response.getData()!=null){
            if(account_inquiry_response.getData().getImageData()!=null) {
                account_inquiry_response.getData().setImageData("");
            }
        }
        logger.info("checker.....");
        return ResponseEntity.status(201).body(gson.toJson(account_inquiry_response));
    }

    @ResponseBody
    @CrossOrigin(origins = "http://172.32.93.111:2200")
    //@CrossOrigin(origins = "http://172.32.93.111:2200")
    @RequestMapping(path="/otc_account_inquiry",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity perfomAccInquiryForOTC(@RequestBody String otcacct_inquiry){
        OtcAcctInquiry otcAcctInquiry = gson.fromJson(otcacct_inquiry,OtcAcctInquiry.class);
        logger.info(otcacct_inquiry);
        logger.info(gson.toJson(otcAcctInquiry));
        Transactions transactions = new Transactions();
        transactions.setOperational_id(transactionOperationsRepository.findTransaction_OperationIdByAction("ACCT_INQUIRY"));
        transactions.setBank_income(0.0);
        transactions.setAgent_commision(0.0);
        transactions.setExcise_duty(0.0);
        transactions.setAgent_id(1);
        transactions.setAccount_from(otcAcctInquiry.getAccount_number());
        transactions.setReceipt_number(LocalDateTime.now().toString().replace(":","").replace(".","").replace("-",""));
        AccountInquiryResponse accountInquiryResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(transactions,API_USERNAME),transactions.getReceipt_number(),"ACCT_INQUIRY"),AccountInquiryResponse.class);

        return ResponseEntity.status(201).body(gson.toJson(accountInquiryResponse));

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,path = "/reverse",consumes = "application/json",produces = "application/json")
    public ResponseEntity performTransactionReversal(@RequestBody String terminal_transId) {
        ReversalRequest reversalRequest = gson.fromJson(terminal_transId, ReversalRequest.class);
        Transactions transactionToReverse = transactionRDBMSRepository.findTimeOutTransactionByTerminal_trans_id(reversalRequest.getTerminal_trans_id()) != null ? transactionRDBMSRepository.findTimeOutTransactionByTerminal_trans_id(reversalRequest.getTerminal_trans_id()) : new Transactions();
                    /**REVERSAL ON TRANSACTION *****/
        if (transactionToReverse.terminal_trans_id != null) {
            ReversalResponse reversalResponse = new ReversalResponse();
            if (transactionToReverse.getStatus().equalsIgnoreCase("F")) {
                reversalResponse.setResponse_code("250");
                reversalResponse.setResponse_mesage("Transaction failed");

            } else {
                String receipt_number = receiptManager.generateReceiptNumber(transactionToReverse.getAgent_id());
                transactionToReverse.setReceipt_number(receipt_number);
                /****CALL COMPAS REST SERVICE TO SEND TRANSACTION TO FINNACLE***/
                String responseData = restServiceConfiguration.RestServiceConfiguration(protocol, SERVICE_IP, SERVICE_PORT, SERVICE_ENDPOINT, "POST", transactionsToBank.prepareTransactionsToBank(transactionToReverse, API_USERNAME), transactionToReverse.getReceipt_number(), transactionOperationsRepository.findTransaction_OperationActionById(transactionToReverse.getOperational_id()));
                     reversalResponse = gson.fromJson(responseData, ReversalResponse.class);
                if (reversalResponse.getResponse_code().equalsIgnoreCase("000")) {
                    //update reversed transaction to status 'R'
                    transactionRDBMSRepository.updateReversedTransaction(transactionToReverse.getTerminal_trans_id());

                } else {
                    //do nothing
                }
            }
            return ResponseEntity.status(201).body(reversalResponse);

        }
            /***REVERSAL ON INQUIRY ******/
        else{
            ReversalResponse reversalResponse = new ReversalResponse();
            InquiriesRequestData inquiriesRequestToReverse = inquiriesRequestDataRepository.findInquiriesRequestDataByTerminal_trans_id(reversalRequest.getTerminal_trans_id());
            if(inquiriesRequestToReverse.getStatus().equalsIgnoreCase("S")){
                reversalResponse.setResponse_code("250");
                reversalResponse.setResponse_mesage("TransactionFailed");
            }
            else{
                String receipt_number = receiptManager.generateReceiptNumber(Integer.parseInt(inquiriesRequestToReverse.getTellerId()));
                inquiriesRequestToReverse.setTransId(receipt_number);
                Transactions transactions = mapInquiriesRequestToTransaction(inquiriesRequestToReverse);
                /****CALL COMPAS REST SERVICE TO REVERSE TRANSACTION*************/
                String responseData = restServiceConfiguration.RestServiceConfiguration(protocol, SERVICE_IP, SERVICE_PORT, SERVICE_ENDPOINT, "POST", transactionsToBank.prepareTransactionsToBank(transactions, API_USERNAME), transactions.getReceipt_number(), transactionOperationsRepository.findTransaction_OperationActionById(transactions.getOperational_id()));
                reversalResponse = gson.fromJson(responseData,ReversalResponse.class);
                if (reversalResponse.getResponse_code().equalsIgnoreCase("000")) {
                    //update reversed transaction to status 'R'
                    inquiriesRequestDataRepository.updateReversedInquiriesRequestData(inquiriesRequestToReverse.getTerminal_trans_id());
                } else {
                    //do nothing
                }
            }
            return ResponseEntity.status(201).body(reversalResponse);
        }

    }

}


