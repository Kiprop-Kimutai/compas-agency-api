package compas.transaction.passwordpolicy;

import com.google.gson.Gson;
import compas.device.Issued_DeviceRepository;
import compas.models.Issued_Device;
import compas.models.OTP;
import compas.models.Transaction;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.Message;
import compas.restservice.RestServiceConfig;
import compas.transaction.TransactionRDBMSRepository;
import compas.transaction.TransactionRepository;
import compas.transactionschannel.TransactionsToBank;
import org.apache.el.stream.*;
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
    @Autowired(required = false)
    private TransactionRepository transactionRepository;
    @Autowired(required = false)
    private TransactionRDBMSRepository transactionRDBMSRepository;
    @Autowired
    @Value("${api.username}")
    private String apiUsername;
    @Autowired(required = false)
    private Issued_DeviceRepository issued_deviceRepository;
    private TransactionsToBank transactionsToBank = new TransactionsToBank();
    private Logger logger = LoggerFactory.getLogger(OTPController.class);
    private Gson gson = new Gson();
    Transaction verifiedTransaction;
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
        String responseBody = restServiceConfig.RestServiceConfiguration("/api/OTP","POST",gson.toJson(otp));
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
                //verifiedTransaction = transactionRDBMSRepository.updateAuthenticatedTransactionByReceiptNumber(successfulOTP.getReceipt_number());
                List<Transaction> verifiedCacheTransactions = transactionRepository.updateTransactionFlagWithMatchingReceipt(successfulOTP.getReceipt_number(), otp.getPassword());
                verifiedCacheTransactions.forEach((verifiedOTPTxn) -> {
                    logger.info("Matching transactions:::>>>>");
                    logger.info(verifiedOTPTxn.getString());
                    transactionRDBMSRepository.save(verifiedOTPTxn);
                                    //SEND TRANSACTION TO BANK REST SERVICE
                    //apiResponse = gson.fromJson(restServiceConfig.RestServiceConfiguration("/api/OTP","POST",gson.toJson(verifiedOTP)),ApiResponse.class);
                    apiResponse = gson.fromJson(restServiceConfig.RestServiceConfiguration("/api/OTP","POST",transactionsToBank.prepareTransactionsToBank(verifiedOTPTxn,apiUsername)),ApiResponse.class);
                    if(apiResponse.getCode() ==201){
                        transactionRDBMSRepository.updateProcessedTransactionByReceiptNumber(verifiedOTPTxn.getReceipt_number());
                        transactionRepository.updateProcessedTransaction(verifiedOTPTxn.getReceipt_number());
                        verifiedTransaction = verifiedOTPTxn;
                        //return ResponseEntity.status(201).body(gson.toJson(verifiedOTPTxn));
                    }
                    else{
                        verifiedTransaction = new Transaction();
                    }
                    //verifiedTransaction = verifiedOTPTxn;
                });
            });
            return ResponseEntity.status(201).body(gson.toJson(verifiedTransaction));
        }
    }

       // return ResponseEntity.status(201).body(gson.toJson(verifiedTransaction));
    }

    public String generateOTPPassword(Transaction transaction,String receipt_number){
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
       otp.setAccount_from(transaction.getAccount_from());
       otp.setAccount_to(transaction.getAccount_to());
       otp.setAgentId(transaction.getAgent_id());
       logger.info("<<>>"+transaction.getAgent_id());
       //List<Issued_Device> issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transaction.getAgent_id());
       Integer deviceId = issued_deviceRepository.findIssued_DeviceByAgent_id(transaction.getAgent_id());
       //otp.setDeviceId(issued_deviceRepository.findIssued_DeviceByAgent_id(transaction.getAgent_id()));
        otp.setDeviceId(deviceId);
       otp.setActive(true);
       otp.setSuccess(false);
       otp.setReceipt_number(receipt_number);
       otp.setOperation_id(transaction.getOperational_id());
       otp.setPassword(password);
       otpRepository.savePassword(otp);
       String responseBody = restServiceConfig.RestServiceConfiguration("/api/OTP","POST",gson.toJson(otp));
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
