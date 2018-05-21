package compas.transaction;

import com.google.gson.Gson;
import compas.authentications.AuthenticationModeRepository;
import compas.authentications.ValidateTransactions;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Device;
import compas.models.Issued_Device;
import compas.models.Transaction;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@RestController
@RequestMapping(path="/api/compas/pbu/agency")
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
    private TransactionOperationRepository transactionOperationRepository;
    private TransactionRepository transactionRepository = new TransactionRepository();
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    String receipt_number = "";
    Issued_Device issued_device;
    Device device;
    Message responseMessage = new Message();

    @RequestMapping(path="/test",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity test(){
        return ResponseEntity.ok("end point up...");
    }

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
                     device = deviceRepository.findDeviceById(issued_device.getDeviceId());
                 });
             }
             else{
                 responseMessage.setMessage("DEVICE ISSUANCE ERROR.Device not issued to agemt");
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
        if(validateTransactions.authenticateAgent(transaction.getAgent_id()) && validateTransactions.authenticateIssuedDevice(issued_device.getId(),transaction.getAgent_id()) && validateTransactions.authenticateDevice(device.getId()) && validateTransactions.authenticateTransactionLimits(transaction.getAmount(),transaction.getOperational_id(),transaction.getAgent_id()) && validateTransactions.authenticateAccount(transaction.getAccount_to()) &&  validateTransactions.authenticateAccount(transaction.getAccount_from())){
                //generate receipt_number
                receipt_number = receiptManager.generateReceiptNumber(transaction.getAgent_id());
        }
        else {
            logger.info("TRANSACTION AUTH FAILED::::OVERALL FAILURE");
            responseMessage.setMessage("TRANSACTION AUTH FAILED");
            return ResponseEntity.status(403).body(gson.toJson(responseMessage));
        }
                    //GENERATE receipt number
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
        String transactionMode = authenticationModeRepository.findAuth_ModeById(transaction.getAuth_mode()).getMode();
        if(transactionMode.equalsIgnoreCase("OTP")){
            //NOW GENERATE OTP and send to CBS
            otpController.generateOTPPassword(transaction,receipt_number);
            //update flag to "P"
            transaction.setStatus("P");
            //calculate tariffs and get processed transaction
            Transaction processedTransaction = tariffManager.setTariffCharges(transaction);
            transactionRepository.saveTransaction(processedTransaction);
            //Transaction validatedTransaction = transactionRDBMSRepository.save(processedTransaction);
            responseMessage.setMessage("OTP generation success");
            return ResponseEntity.status(210).body(gson.toJson(responseMessage));
        }
        else{

        }
        //carry out repository operations
        transactionRepository.saveTransaction(transaction);
        Transaction returnTx = transactionRDBMSRepository.save(transaction);
        //forward received transaction to CBS for processing
        Transaction response = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(returnTx)),Transaction.class);
        return ResponseEntity.status(201).body(returnTx);
    }

}
