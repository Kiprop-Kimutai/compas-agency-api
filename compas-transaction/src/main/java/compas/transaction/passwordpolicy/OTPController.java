package compas.transaction.passwordpolicy;

import com.google.gson.Gson;
import compas.device.Issued_DeviceRepository;
import compas.models.OTP;
import compas.models.SMS;
import compas.models.Transaction_Operation;
import compas.models.Transactions;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.Message;
import compas.models.apiresponse.SmsResponse;
import compas.models.bankoperations.AccountInquiry.ACResponseData;
import compas.models.bankoperations.AccountInquiry.AccountInquiryResponse;
import compas.restservice.RestServiceConfig;
import compas.tariffs.TariffManager;
import compas.transaction.TransactionController;
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
//@RequestMapping(path = "/api/compas")
@RequestMapping(path = "/compas")
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
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TariffManager tariffManager = new TariffManager();
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
        otp.setRequest_time(request_time);
        otp.setActive(true);
        String validOTP = otpRepository.savePassword(otp).getPassword();
        String responseBody = restServiceConfig.RestServiceConfiguration(otp_protocol,OTP_SERVER_IP,OTP_SERVER_PORT,OTP_SERVER_ENDPOINT,"POST",gson.toJson(otp),"","",true);
        return ResponseEntity.status(201).body(responseBody);
    }

    @Transactional
    @RequestMapping(path = "/verifyOTP",method  = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity verifyOTP(@RequestBody String otpString) {
        logger.info(String.format("OTP request["+otpString+"]"));
        //verify OTP
        OTP otp = gson.fromJson(otpString, OTP.class);
        logger.info(gson.toJson(otp));
        Optional<Integer> x;
        List<OTP> verifiedOTP;
        //get successfull OTP by receipt_number
        verifiedOTP = otpRepository.findActiveMatchingOTP(otp);
        logger.info("Found transaction::::"+verifiedOTP.size());
        if (verifiedOTP.size() == 0) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(110);
            apiResponse.setMessage("INVALID OTP");
            return ResponseEntity.status(201).body(apiResponse);
        }
        else {
            //match receipt_number and update flag in RDDM and cache repositories
            verifiedOTP.forEach((successfulOTP) -> {
                //get matching receipt_numbers in transactions and update accordingly
                List<Transactions> verifiedCacheTransactions = transactionRepository.updateTransactionFlagWithMatchingReceipt(successfulOTP.getReceipt_number(), otp.getPassword());
                verifiedCacheTransactions.forEach((verifiedOTPTxn) -> {

                    Transaction_Operation transaction_operation = transactionOperationsRepository.findTransaction_OperationById(verifiedOTPTxn.getOperational_id());
                    logger.info("OPERATION:::"+transaction_operation.getAction().toUpperCase());
                    if (transaction_operation.getAction().equalsIgnoreCase("BAL") || transaction_operation.getAction().equalsIgnoreCase("MINI")
                            || transaction_operation.getAction().equalsIgnoreCase("FULL") || transaction_operation.getAction().equalsIgnoreCase("ACCT_INQUIRY") || transaction_operation.getAction().equalsIgnoreCase("BAL_LIST")) {

                        logger.info("Verifying OTP FOR INQUIRY TRANSACTION.......");
                        apiResponse = transactionController.processAuthenticatedInquiry(verifiedOTPTxn);
                        if(apiResponse.Data!=null) {
                            apiResponse.getData().setExercise_duty(verifiedOTPTxn.getExcise_duty());
                            apiResponse.getData().setBank_income(verifiedOTPTxn.getBank_income());
                            apiResponse.getData().setAgent_commission(verifiedOTPTxn.getAgent_commision());
                        }
                    }
                    /***NORMAL TRANSACTIONS GO HERE ******/
                    else{

                        /***SET TRANSACTION CHARGES HERE ****/
                        logger.info("checker....");
                        logger.info(gson.toJson(verifiedOTPTxn, Transactions.class));
                        Transactions processedTransaction = tariffManager.setCharges(verifiedOTPTxn);
                        logger.info("<-----------check charges here---------->");
                        logger.info(String.format("processed transaction[" + gson.toJson(processedTransaction) + "]"));
                        transactionRDBMSRepository.save(processedTransaction);
                        /**===========================CALL COMPAS BRIDGE REST SERVICE TO PUSH TRANSACTIONS TO CBS MIDDLEWARE ====================================**/
                        String responseData = restServiceConfig.RestServiceConfiguration(protocol, SERVICE_IP, SERVICE_PORT, SERVICE_ENDPOINT, "POST", transactionsToBank.prepareTransactionsToBank(processedTransaction, API_USERNAME), verifiedOTPTxn.getReceipt_number(), transactionOperationsRepository.findTransaction_OperationActionById(verifiedOTPTxn.getOperational_id()),true);
                        logger.info(responseData);
                        apiResponse = gson.fromJson(responseData, ApiResponse.class);
                        switch (apiResponse.getCode()) {
                            case 201:
                                if(apiResponse.getResponse_code().equalsIgnoreCase("000")) {
                                    apiResponse = transactionController.ProcessCBSResponse(verifiedOTPTxn.getReceipt_number(), apiResponse, "");
                                    apiResponse.getData().setAcctName(processedTransaction.getCustomer_name());
                                    apiResponse.getData().setReceipt_number(processedTransaction.getReceipt_number());
                                    apiResponse.getData().setAgent_commission(processedTransaction.getAgent_commision());
                                    apiResponse.getData().setBank_income(processedTransaction.getBank_income());
                                    apiResponse.getData().setExercise_duty(processedTransaction.getExcise_duty());
                                }
                                else if(apiResponse.getResponse_code().equalsIgnoreCase("114")){
                                    apiResponse.setCode(156);
                                }
                                else if(apiResponse.getResponse_code().equalsIgnoreCase("303")){
                                    apiResponse.setCode(153);
                                }
                                break;
                            default:
                                apiResponse.setMessage(String.format("%s TRANSACTION WITH TRANSACTION ID %s FAILED", verifiedOTPTxn.getNarration().toUpperCase(), verifiedOTPTxn.getReceipt_number()));
                                logger.info(gson.toJson(apiResponse));
                        }

                    }

                });
            });
            logger.info("TRANSACTION_RESPONSE{"+gson.toJson(apiResponse)+"}");
            return ResponseEntity.status(201).body(gson.toJson(apiResponse));
        }

    }

    public String generateOTPPassword(Transactions transactions, String receipt_number){
        SESSION_RECEIPT_NUMBER = receipt_number;
        logger.info("Init...");
        Transaction_Operation transaction_operation = transactionOperationsRepository.findTransaction_OperationById(transactions.getOperational_id());
        OTP otp = new OTP();
        SMS sms = new SMS();
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
        logger.info("<<agent-id>>"+ transactions.getAgent_id());
        Integer deviceId = issued_deviceRepository.findIssued_DeviceByAgent_id(transactions.getAgent_id());
        otp.setDeviceId(deviceId);
        otp.setActive(true);
        otp.setSuccess(false);
        otp.setReceipt_number(receipt_number);
        otp.setOperation_id(transactions.getOperational_id());
        otp.setPassword(password);
        otpRepository.savePassword(otp);
        /**** PERFORM ACCT INQUIRY TO GET PHONE NUMBER******/
        String acctInquiryResponse = transactionController.performAcctInquiry(gson.toJson(transactions));
        logger.info("check acct inquiry response here<<<<<<<<<<!!!!!!!!!!!!!!!!!!!!>>>>>>>>>>");
        ApiResponse accountInquiryResponse = gson.fromJson(acctInquiryResponse,ApiResponse.class);

        /***capture scenario here************/
        if(accountInquiryResponse.Data==null) {
            logger.info("<<<<<<<2222>>>>>>>");
            SmsResponse smsResponse = new SmsResponse();
            smsResponse.setCode(311);
            smsResponse.setResponse_code("300");
            return gson.toJson(smsResponse);
        }
        //build SMS request here
        sms.setPhoneNo(accountInquiryResponse.getData().getPhone() != null ? accountInquiryResponse.getData().getPhone() : "");
        sms.setNarration("Your one time password for "+transaction_operation.getAction().toUpperCase() +" is " +password +". You can use it at any PostBank agent point");
        sms.setTransId(password);
        String responseBody = restServiceConfig.RestServiceConfiguration(otp_protocol, SERVICE_IP, SERVICE_PORT, SERVICE_ENDPOINT, "POST", transactionsToBank.prepareTransactionsToBank(transactions, API_USERNAME, sms), password, "SMS",true);
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

    @ResponseBody
    @RequestMapping(path="/generateSMS",consumes = "application/json",produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity sendSMS(@RequestBody String smsRequest){
        Date requestedDate = new Date();
        Long requestedDateLong = requestedDate.getTime();
        SMS sms =gson.fromJson(smsRequest,SMS.class);
        Integer lastThreeRandomNos = generateLastThreeRandomNumbers(1000,100).intValue();
        String password = requestedDateLong.toString().substring(requestedDateLong.toString().length()-3,requestedDateLong.toString().length()).concat(lastThreeRandomNos.toString());
        sms.setTransId(password);
        String responseBody = restServiceConfig.RestServiceConfiguration(otp_protocol, SERVICE_IP, SERVICE_PORT, SERVICE_ENDPOINT, "POST", transactionsToBank.prepareTransactionsToBank(new Transactions(), API_USERNAME, sms), password, "SMS",true);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<CHECK POINT>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info(responseBody);
        //return responseBody;
        return ResponseEntity.status(201).body(responseBody);
    }

}
