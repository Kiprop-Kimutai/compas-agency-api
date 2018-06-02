package compas.transaction;

import com.google.gson.Gson;
import compas.authentications.AuthenticationModeRepository;
import compas.authentications.ValidateTransactions;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Device;
import compas.models.Issued_Device;
import compas.models.Transaction;
import compas.models.apiresponse.ApiResponse;
import compas.models.apiresponse.Message;
import compas.restservice.RestServiceConfig;
import compas.tariffs.TariffManager;
import compas.transaction.agencyreceipt.ReceiptManager;
import compas.transaction.passwordpolicy.OTPController;
import compas.txn_params.TransactionOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Repository
@RestController
@RequestMapping(path="/compas/pbu/agency")
public class TransactionController {
    private Gson gson = new Gson();
    @Autowired(required = false)
    private OTPController otpController;
    @Autowired(required = false)
    private TariffManager tariffManager = new TariffManager();
    @Autowired(required = false)
    private ReceiptManager receiptManager;
    @Autowired(required = false)
    private ValidateTransactions validateTransactions;
    @Autowired(required = false)
    private RestServiceConfig restServiceConfiguration;
    @Autowired(required = false)
    private TransactionRDBMSRepository transactionRDBMSRepository;
    @Autowired(required = false)
    private DeviceRepository deviceRepository;
    @Autowired(required = false)
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired(required = false)
    private AuthenticationModeRepository authenticationModeRepository;
    @Autowired(required = false)
    private TransactionOperationRepository transactionOperationRepository;

    private TransactionRepository transactionRepository = new TransactionRepository();
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    String receipt_number = "";
    Issued_Device issued_device;
    Device device;
    Message responseMessage = new Message();
    ApiResponse apiResponse = new ApiResponse();

    @RequestMapping(path="/test",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity test(){
        return ResponseEntity.ok("end point up...");
    }

    @Transactional
    @RequestMapping(path="/post_transaction",method=RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity postTransaction(@RequestBody String transactionString){
        logger.info(transactionString);
        Transaction transaction = gson.fromJson(transactionString,Transaction.class);
        try{
             List<Issued_Device>issued_devices = issued_deviceRepository.findIssued_DeviceByAgentId(transaction.getAgent_id());
             if(issued_devices.size()>0){
                 issued_devices.forEach((x) ->{
                     issued_device = x;
                     logger.info("issued device");
                     logger.info(x.getString());
                     device = deviceRepository.findDeviceById(issued_device.getDeviceId());
                     logger.info("issued device");
                     logger.info(device.getString());
                 });
             }
             else{
                 responseMessage.setMessage("DEVICE ISSUANCE ERROR.Device not issued to agent");
                 logger.info(responseMessage.getMessage());
                 return ResponseEntity.status(403).body(responseMessage.getMessage());
             }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(transaction);
        }


        logger.info("Transaction Request::"+transaction);
                    //FIRST CHECKPOINT>>>>>>VALIDATE TRANSACTION
        if(!validateTransactions.authenticateAgent(transaction.getAgent_id())){
            String message = String.format("Agent %d  is INACTIVE or NOT registered",transaction.getAgent_id());
            responseMessage.setMessage(message);
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }
        if(!validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id())){
            String message = String.format("Device  = %d  is not isued to agent %d ",issued_device.getDeviceId(),issued_device.getAgent_id());
            responseMessage.setMessage(message);
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }
        if(!validateTransactions.authenticateDevice(device.getId())){
            String message = String.format("Device % is not registered or active",device.getId());
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }
        if(!validateTransactions.authenticateTransactionLimits(transaction.getAmount(),transaction.getOperational_id(),transaction.getAgent_id())){
            responseMessage.setMessage("Transaction limits for operation type exceeded");
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }
        if(!(validateTransactions.authenticateAccount(transaction.getAccount_from())&& validateTransactions.authenticateAccount(transaction.getAccount_to()))){
            responseMessage.setMessage("Invalid account number");
            return ResponseEntity.status(300).body(gson.toJson(responseMessage.getMessage()));
        }
        /*************************************************************************************************8/
   /*        if(validateTransactions.authenticateAgent(transaction.getAgent_id()) && validateTransactions.authenticateIssuedDevice(issued_device.getDeviceId(),issued_device.getAgent_id()) && validateTransactions.authenticateDevice(device.getId()) && validateTransactions.authenticateTransactionLimits(transaction.getAmount(),transaction.getOperational_id(),transaction.getAgent_id()) && validateTransactions.authenticateAccount(transaction.getAccount_to()) &&  validateTransactions.authenticateAccount(transaction.getAccount_from())){
                //generate receipt_number
                receipt_number = receiptManager.generateReceiptNumber(transaction.getAgent_id());
        }
        else {
            logger.info("TRANSACTION AUTH FAILED::::OVERALL FAILURE");
            responseMessage.setMessage("TRANSACTION AUTH FAILED");
            return ResponseEntity.status(400).body(gson.toJson(responseMessage));
        }
    */
        /**************************************************************************************************/
                    //GENERATE receipt_number
        receipt_number = receiptManager.generateReceiptNumber(transaction.getAgent_id());
                    //SET receipt number
        transaction.setReceipt_number(receipt_number);
                    //DETERMINE AND SET CASH INS and CASH OUTS
        Integer cash_flow_id = transactionOperationRepository.selectCashFlowId(transaction.getOperational_id());
        if(cash_flow_id == 1){
            transaction.setCash_in(transaction.amount);
            transaction.setCash_out(0.0);
        }
        else if(cash_flow_id == 2){
            transaction.setCash_out(transaction.amount);
            transaction.setCash_in(0.0);
        }
                    //CHECK TRANSACTION AUTH MODE
        //String transactionMode = authenticationModeRepository.findAuth_ModeById(transaction.getAuth_mode()).getMode();
        String transactionMode = authenticationModeRepository.findAuth_ModeById(transaction.getAuth_mode()).getMode();
        logger.info("AUTHENTICATION:::",transactionMode);
        logger.info(transactionMode);
        if(transactionMode.equalsIgnoreCase("OTP")){
            //NOW GENERATE OTP and send to CBS
            String response = otpController.generateOTPPassword(transaction,receipt_number);
            //check response status
            apiResponse = gson.fromJson(response,ApiResponse.class);
            if(apiResponse.getCode() !=201){
                responseMessage.setMessage("OTP GENERATION FAILED");
                return ResponseEntity.status(apiResponse.getCode()).body(gson.toJson(responseMessage));
            }
            //update flag to "P"
            transaction.setStatus("P");
            //calculate tariffs and get processed transaction
            Transaction processedTransaction = tariffManager.setTariffCharges(transaction);
            transactionRepository.saveTransaction(processedTransaction);
            //Transaction validatedTransaction = transactionRDBMSRepository.save(processedTransaction);
            responseMessage.setMessage("OTP generation success");
            return ResponseEntity.status(201).body(gson.toJson(responseMessage));
        }
        else if(transactionMode.equalsIgnoreCase("PIN")){
            transaction.setStatus("I");
            Transaction processedTransaction = tariffManager.setTariffCharges(transaction);
            transactionRepository.saveTransaction(processedTransaction);
            transactionRDBMSRepository.save(processedTransaction);
            apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(processedTransaction)),ApiResponse.class);
            if(apiResponse.getCode() == 201){
                logger.info("RECEIPT NUMBER TO UPDATE:::" +processedTransaction.getReceipt_number());
                transactionRDBMSRepository.updateProcessedTransactionByReceiptNumber(processedTransaction.getReceipt_number());
                Transaction  successfulProcessedTxn = transactionRepository.updateProcessedTransaction(processedTransaction.getReceipt_number());
                return ResponseEntity.status(201).body(successfulProcessedTxn);
            }

                return ResponseEntity.status(400).body(gson.toJson(apiResponse));

        }
        else if(transactionMode.equalsIgnoreCase("BIO")){
            transaction.setStatus("I");
            Transaction processedTransaction = tariffManager.setTariffCharges(transaction);
            transactionRepository.saveTransaction(processedTransaction);
            transactionRDBMSRepository.save(processedTransaction);
            //Transaction successfulTransaction = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(processedTransaction)),Transaction.class);
            apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(processedTransaction)),ApiResponse.class);
            if(apiResponse.getCode() ==201){
                transactionRDBMSRepository.updateProcessedTransactionByReceiptNumber(processedTransaction.getReceipt_number());
                Transaction  verySuccessfulTxn = transactionRepository.updateProcessedTransaction(processedTransaction.getReceipt_number());
                return ResponseEntity.status(201).body(verySuccessfulTxn);
            }
            else{
                return ResponseEntity.status(400).body(gson.toJson(apiResponse));
            }
        }
        else{
                //transactions needing no AUTHENTICATION i.e cash deposits
            Transaction processedTransaction = tariffManager.setTariffCharges(transaction);
            transactionRepository.saveTransaction(processedTransaction);
            Transaction returnTx = transactionRDBMSRepository.save(processedTransaction);
            //forward received transaction to CBS for processing
            apiResponse = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(returnTx)),ApiResponse.class);
            if(apiResponse.getCode() == 201){
                Transaction successfulProcessedTransaction = gson.fromJson(apiResponse.getMessage(),Transaction.class);
                transactionRDBMSRepository.updateProcessedTransactionByReceiptNumber(successfulProcessedTransaction.getReceipt_number());
                transactionRepository.updateProcessedTransaction(transaction.getReceipt_number());
                return ResponseEntity.status(201).body(successfulProcessedTransaction);
            }
            return ResponseEntity.status(400).body(gson.toJson(apiResponse.getMessage()));

        }
        //return ResponseEntity.status(500).body(gson.toJson(responseMessage));
        //carry out repository operations
/*        transactionRepository.saveTransaction(transaction);
        Transaction returnTx = transactionRDBMSRepository.save(transaction);
        //forward received transaction to CBS for processing
        Transaction response = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(returnTx)),Transaction.class);
        return ResponseEntity.status(201).body(returnTx);*/
    }

}
