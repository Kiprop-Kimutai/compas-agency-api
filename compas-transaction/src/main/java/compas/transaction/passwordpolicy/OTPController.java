package compas.transaction.passwordpolicy;

import com.google.gson.Gson;
import compas.device.Issued_DeviceRepository;
import compas.models.OTP;
import compas.models.Transactions;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.Message;
import compas.restservice.RestServiceConfig;
import compas.transaction.TransactionRDBMSRepository;
import compas.transaction.TransactionRepository;
import compas.transactionschannel.TransactionsToBank;
import compas.txn_params.TransactionOperationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Optional;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@RestController
@RequestMapping(path = "/api")
public class OTPController {
    private OTPRepository otpRepository = new OTPRepository();
    @Autowired(required = false)
    private RestServiceConfig restServiceConfig;
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;
    @Autowired
    private TransactionOperationsRepository transactionOperationsRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private TransactionsToBank transactionsToBank;
    private TransactionRepository transactionRepository = new TransactionRepository();

    /*********CBS BRIGDE CONFIG****************/
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
    /****************SMS SERVER CONFIG*****************/
    @Value("${api.otp_server.IP}")
    public String OTP_SERVER_IP;
    @Value("${api.otp_server.PORT}")
    public String OTP_SERVER_PORT;
    @Value("${api.otp.endpoint}")
    public String OTP_SERVER_ENDPOINT;
    @Value("${api.otp.protocol}")
    private String otp_protocol;


    private Logger logger = LoggerFactory.getLogger(OTPController.class);
    private Gson gson = new Gson();
    Transactions verifiedTransactions;
    Message responseMessage = new Message();
    String SESSION_RECEIPT_NUMBER = "";
    ApiResponse apiResponse = new ApiResponse();



    @RequestMapping(path="/generateOTP",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    @ResponseBody
    public ResponseEntity generateOTP(@RequestBody String otpRequestString){
        Date request_time = new Date();
        OTP otp = gson.fromJson(otpRequestString,OTP.class);
        //otp.setTransaction_type("cash_transaction");
       // otp.setOperation_id(transaction.get);
        //otp.setPassword(generateOTPPassword());
        otp.setRequest_time(request_time);
        otp.setActive(true);
        String validOTP = otpRepository.savePassword(otp).getPassword();
        String responseBody = restServiceConfig.RestServiceConfiguration(otp_protocol,OTP_SERVER_IP,OTP_SERVER_PORT,OTP_SERVER_ENDPOINT,"POST",gson.toJson(otp),"","");
        return ResponseEntity.status(201).body(responseBody);
    }

    @Transactional
    @RequestMapping(path = "/verifyOTP",method  = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity verifyOTP(@RequestBody String otpString) {
        //verify OTP
        OTP otp = gson.fromJson(otpString, OTP.class);
        Optional<Integer> x;
        List<OTP> verifiedOTP;
        logger.info("PASSWORD::" + otp.getPassword());
        List<OTP> updatedOTP = otpRepository.updateOTPStatus(otp.getPassword());
        logger.info("About to fail .....");
        logger.info("Record size::" + updatedOTP.size());
        if (updatedOTP.size() == 0) {
                responseMessage.setMessage("INVALID OTP");
                return ResponseEntity.status(400).body(gson.toJson(responseMessage));
        } else {
            //get successfull OTP by receipt_number
            verifiedOTP = otpRepository.getSuccessfulOTPByReceiptNumber(updatedOTP.get(0).getReceipt_number());

        if (verifiedOTP.size() == 0) {
            logger.info("size" + verifiedOTP.size());
            responseMessage.setMessage("INVALID OTP");
            logger.info(responseMessage.getMessage());
            return ResponseEntity.status(400).body(gson.toJson(responseMessage));
        } else {
            //match receipt_number and update flag in RDDM and cache repositories
            verifiedOTP.forEach((successfulOTP) -> {
                //get matching receipt_numbers in transactions and update accordingly
                //verifiedTransactions = transactionRDBMSRepository.updateAuthenticatedTransactionByReceiptNumber(successfulOTP.getReceipt_number());
                List<Transactions> verifiedCacheTransactions = transactionRepository.updateTransactionFlagWithMatchingReceipt(successfulOTP.getReceipt_number(), otp.getPassword());
                verifiedCacheTransactions.forEach((verifiedOTPTxn) -> {
                    logger.info("Matching transactions:::>>>>");
                    logger.info(verifiedOTPTxn.getString());
                    transactionRDBMSRepository.save(verifiedOTPTxn);
                                    //SEND TRANSACTION TO BANK REST SERVICE
                    //apiResponse = gson.fromJson(restServiceConfig.RestServiceConfiguration("/api/OTP","POST",gson.toJson(verifiedOTP)),ApiResponse.class);

                    /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
                    String responseData =  restServiceConfig.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",transactionsToBank.prepareTransactionsToBank(verifiedOTPTxn,API_USERNAME),verifiedOTPTxn.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(verifiedOTPTxn.getOperational_id()));
                    logger.info(responseData);
                    //apiResponse = gson.fromJson(restServiceConfig.RestServiceConfiguration("/api/OTP","POST",transactionsToBank.prepareTransactionsToBank(verifiedOTPTxn,apiUsername),verifiedOTPTxn.getReceipt_number(),transactionOperationsRepository.findTransaction_OperationActionById(verifiedOTPTxn.getOperational_id())),ApiResponse.class);
                    if(apiResponse.getCode() ==201){
                        transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(verifiedOTPTxn.getReceipt_number());
                        transactionRepository.updateProcessedTransaction(verifiedOTPTxn.getReceipt_number());
                        verifiedTransactions = verifiedOTPTxn;
                        //return ResponseEntity.status(201).body(gson.toJson(verifiedOTPTxn));
                    }
                    else{
                        verifiedTransactions = new Transactions();
                    }
                    //verifiedTransactions = verifiedOTPTxn;
                });
            });
            return ResponseEntity.status(201).body(gson.toJson(verifiedTransactions));
        }
    }

       // return ResponseEntity.status(201).body(gson.toJson(verifiedTransactions));
    }

    public String generateOTPPassword(Transactions transactions, String receipt_number){
        SESSION_RECEIPT_NUMBER = receipt_number;
        logger.info("Init...");
        OTP otp = new OTP();
       Date requestedDate = new Date();
        Long requestedDateLong = requestedDate.getTime();
        System.out.println(requestedDateLong);
        Integer  lastThreeRandomNos = generateLastThreeRandomNumbers(1000,100).intValue();
        String password = requestedDateLong.toString().substring(requestedDateLong.toString().length()-3,requestedDateLong.toString().length()).concat(lastThreeRandomNos.toString());
        logger.info(password);
       otp.setRequest_time(requestedDate);
       otp.setAccount_from(transactions.getAccount_from());
       otp.setAccount_to(transactions.getAccount_to());
       otp.setAgentId(transactions.getAgent_id());
       logger.info("<<>>"+ transactions.getAgent_id());
       //List<Issued_Device> issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transactions.getAgent_id());
       Integer deviceId = issued_deviceRepository.findIssued_DeviceByAgent_id(transactions.getAgent_id());
       //otp.setDeviceId(issued_deviceRepository.findIssued_DeviceByAgent_id(transactions.getAgent_id()));
        otp.setDeviceId(deviceId);
       otp.setActive(true);
       otp.setSuccess(false);
       otp.setReceipt_number(receipt_number);
       otp.setOperation_id(transactions.getOperational_id());
       otp.setPassword(password);
       otpRepository.savePassword(otp);
       String responseBody = restServiceConfig.RestServiceConfiguration(otp_protocol,OTP_SERVER_IP,OTP_SERVER_PORT,OTP_SERVER_ENDPOINT,"POST",gson.toJson(otp),"","");
       logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<CHECK POINT>>>>>>>>>>>>>>>>>>>>>>>>>");
       logger.info(responseBody);
        return responseBody;
    }


    public Double generateLastThreeRandomNumbers(Integer max,Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }


    public  void ManagePasswordPolicy(){
       Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               logger.info("   WATCH TIMER");
           }
       },1000,1000);
    }

}
