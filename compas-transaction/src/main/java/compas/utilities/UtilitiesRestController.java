package compas.utilities;

import com.google.gson.Gson;
import compas.models.utilities.Extras;
import compas.models.utilities.UtilityPayment;
import compas.models.utilities.UtilityRequest;
import compas.models.utilities.UtilityRequestData;
import compas.models.utilities.nwsc.*;
import compas.models.utilities.umeme.UmemePaymentData;
import compas.models.utilities.umeme.UmemePaymentRequest;
import compas.models.utilities.umeme.UmemeValidateData;
import compas.models.utilities.umeme.UmemeValidateRequest;
import compas.models.utilities.ura.*;
import compas.restservice.RestServiceConfig;
import compas.transaction.agencyreceipt.ReceiptManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
@RestController
@RequestMapping(path = "/compas/utilities")
public class UtilitiesRestController {
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
    @Autowired
    private RestServiceConfig restServiceConfiguration;
    @Autowired
    private UtilitiesRequestDataRepository utilitiesRequestDataRepository;
    @Autowired
    private ReceiptManager receiptManager;
    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(UtilitiesController.class);
/*    @ResponseBody
    @RequestMapping(path="/billrequest",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity processBillPaymentRequest(@RequestBody String billRequest){
        UtilityRequest utilityRequest = gson.fromJson(billRequest,UtilityRequest.class);
        return ResponseEntity.status(201).body("ok..");
    }*/

    @ResponseBody
    @RequestMapping(path="/billrequest",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity processBillPaymentRequest(@RequestBody String utilityPaymentRequest){
        logger.info(String.format("Utility payment request::%s\n",utilityPaymentRequest));
        UtilityPayment utilityPayment = gson.fromJson(utilityPaymentRequest,UtilityPayment.class);
        logger.info(gson.toJson(utilityPayment));
        UtilityRequest utilityRequest = gson.fromJson(utilityPaymentRequest,UtilityRequest.class);
        UtilityRequestData utilityRequestData = gson.fromJson(utilityPaymentRequest,UtilityRequestData.class);
        /**SET RequestId here **/
        utilityRequestData.setRequestId(receiptManager.generateReceiptNumber(utilityPayment.getAgent_id()));
        logger.info(gson.toJson(utilityRequestData));
        //Extras extras = utilityPayment.getExtras();
        utilityRequest.setTransactionbody(utilityRequestData);
        logger.info(gson.toJson(utilityRequest));
        String billsoutput = processBills(utilityRequest);
        logger.info(String.format("Processed Bills::%s\n",billsoutput));
        return ResponseEntity.status(201).body(billsoutput);
    }
    public String processBills(UtilityRequest utilityRequest){
        switch(utilityRequest.getUtility().toUpperCase().trim()){
            case "UMEME":
                return processUmemeBills(utilityRequest.getMenu(),utilityRequest.getTransactionbody());
            case "NWSC":
                return processNWSCBills(utilityRequest.getMenu(),utilityRequest.getTransactionbody());
            case "URA":
                return processURABills(utilityRequest.getMenu(),utilityRequest.getTransactionbody());
            case "STARTIMES":
                break;
            case "AZAMTV":
                break;
            case "SIMBATV":
                break;
            case "DSTV":
                break;
            case "GOTV":
                break;
            case "SCHOOLFEES":
                break;
            default:
                return "Invalid bill selection";

        }
        return "";
    }

    public String processUmemeBills(String menu,UtilityRequestData requestData){
        logger.info("process umeme bills init..");
        String responseData = "";
        switch (menu.toUpperCase().trim()){
            case "PAYBILL_UMEME_VALIDATE":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareUmemeCustomerValidation(requestData)),prepareUmemeCustomerValidation(requestData).getData().getRequestId(),"PAYBILL_UMEME_VALIDATE",false);
                 logger.info(responseData);
                 return responseData;
            case "PAYBILL_UMEME_PAYMENT":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareUmemePaymentRequest(requestData)),prepareUmemePaymentRequest(requestData).getData().getRequestId(),"PAYBILL_UMEME_PAYMENT",false);
                 logger.info(responseData);
                 return responseData;
            default:
                return String.format("{failed:invalid menu selection}");
        }
    }

    public UmemeValidateRequest prepareUmemeCustomerValidation(UtilityRequestData requestData){
        logger.info("umeme customer validation init..");
        UmemeValidateRequest umemeValidateRequest = new UmemeValidateRequest();
        UmemeValidateData validateData = new UmemeValidateData();
        umemeValidateRequest.setUsername(API_USERNAME);
        umemeValidateRequest.setAction("PAYBILL_UMEME_VALIDATE");
        validateData.setBranchId("0001");
        validateData.setCustRef(requestData.getCustRef());
        validateData.setPhoneNo(requestData.getPhoneNo());
        //validateData.setRequestId(requestData.getTerminal_transaction_id());
        validateData.setRequestId(requestData.getRequestId());
        umemeValidateRequest.setData(validateData);
        //save utilities request data
        utilitiesRequestDataRepository.save(requestData);
        logger.info(gson.toJson(umemeValidateRequest));
        return umemeValidateRequest;

    }

    public UmemePaymentRequest prepareUmemePaymentRequest(UtilityRequestData requestData){
        logger.info("umeme payment request init...");
        UmemePaymentRequest umemePaymentRequest = new UmemePaymentRequest();
        UmemePaymentData umemePaymentData = new UmemePaymentData();
        umemePaymentRequest.setUsername(API_USERNAME);
        umemePaymentRequest.setAction("PAYBILL_UMEME_PAYMENT");
        umemePaymentData.setBranchId("001");
        umemePaymentData.setCustRef(requestData.getCustRef());
        umemePaymentData.setPhoneNo(requestData.getPhoneNo());
        umemePaymentData.setAmount(requestData.getAmount());
        umemePaymentData.setRequestId(requestData.getRequestId());
        umemePaymentData.setTransId(requestData.getTransId());
        umemePaymentData.setTransrefNo("");
        umemePaymentRequest.setData(umemePaymentData);
        //save umeme payment data
        utilitiesRequestDataRepository.save(requestData);
        logger.info(gson.toJson(umemePaymentRequest));
        return umemePaymentRequest;

    }

    public String processNWSCBills(String menu,UtilityRequestData utilityRequestData){
        logger.info("process NWSC bills init...");
         String responseData = "";
        switch(menu.toLowerCase().trim()){
            case "select_area_of_residence":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareNWSCAreaSelection(utilityRequestData)),prepareNWSCAreaSelection(utilityRequestData).getData().getRequestId(),"PAYBILL_NWSC_AREAS",false);
                logger.info(responseData);
                return responseData;
            case "validate_customer":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareNwscCustomerValidation(utilityRequestData)),prepareNwscCustomerValidation(utilityRequestData).getData().getRequestId(),"PAYBILL_NWSC_VALIDATE",false);
                logger.info(responseData);
                return responseData;
            case "pay_bill":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareNwscPayment(utilityRequestData)),prepareNwscPayment(utilityRequestData).getData().getRequestId(),"PAYBILL_NWSC_PAYMENT",false);
                logger.info(responseData);
                return responseData;
            default:
                return "";
        }
    }

    public NwscAreasRequest prepareNWSCAreaSelection(UtilityRequestData utilityRequestData ){
        logger.info("prepare NWSC area selection init...");
        NwscAreasRequest nwscAreasRequest = new NwscAreasRequest();
        NwscAreasRequestData nwscAreasRequestData = new NwscAreasRequestData();
        nwscAreasRequest.setUsername(API_USERNAME);
        nwscAreasRequest.setAction("PAYBILL_NWSC_AREAS");
        nwscAreasRequestData.setRequestId(utilityRequestData.getRequestId());
        nwscAreasRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        nwscAreasRequest.setData(nwscAreasRequestData);
        logger.info(gson.toJson(nwscAreasRequest));
        return nwscAreasRequest;
    }

    public NwscValidateRequest prepareNwscCustomerValidation(UtilityRequestData utilityRequestData){
        logger.info("prepare nwsc customer validation init...");
        NwscValidateRequest nwscValidateRequest = new NwscValidateRequest();
        NwscValidateRequestData nwscValidateRequestData = new NwscValidateRequestData();
        nwscValidateRequest.setUsername(API_USERNAME);
        nwscValidateRequest.setAction("PAYBILL_NWSC_VALIDATE");
        nwscValidateRequestData.setAreaId(utilityRequestData.getAreaId());
        nwscValidateRequestData.setCustRef(utilityRequestData.getCustRef());
        nwscValidateRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        nwscValidateRequestData.setRequestId(utilityRequestData.getRequestId());
        nwscValidateRequest.setData(nwscValidateRequestData);
        logger.info(gson.toJson(nwscValidateRequest));
        return nwscValidateRequest;
    }

    public NwscPaymentRequest prepareNwscPayment(UtilityRequestData utilityRequestData){
        logger.info("NWSC payment init..");
        NwscPaymentRequest nwscPaymentRequest = new NwscPaymentRequest();
        NwscPaymentRequestData nwscPaymentRequestData = new NwscPaymentRequestData();
        nwscPaymentRequest.setUsername(API_USERNAME);
        nwscPaymentRequest.setAction("PAYBILL_NWSC_PAYMENT");
        nwscPaymentRequestData.setAmount(Long.parseLong(utilityRequestData.getAmount()));
        nwscPaymentRequestData.setAreaId(utilityRequestData.getAreaId());
        nwscPaymentRequestData.setBranchId("0001");
        nwscPaymentRequestData.setCustRef(utilityRequestData.getCustRef());
        nwscPaymentRequestData.setRequestId(utilityRequestData.getRequestId());
        nwscPaymentRequestData.setTransId(utilityRequestData.getTransId());
        nwscPaymentRequest.setData(nwscPaymentRequestData);
        logger.info(gson.toJson(nwscPaymentRequest));
        return nwscPaymentRequest;
    }


    public String processURABills(String menu,UtilityRequestData utilityRequestData){
        logger.info("process URA bills init...");
        String responseData = "";
        switch(menu.toLowerCase().trim()){
            case "paybill_ura_branch":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareBranchRequest(utilityRequestData)),prepareBranchRequest(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_BRANCH",false);
                logger.info(responseData);
                return responseData;
            case "validate_customer_with_prn":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(preparePRNValidationRequest(utilityRequestData)),preparePRNValidationRequest(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_VALIDATE",false);
                logger.info(responseData);
                return responseData;
            case "payment_with_prn":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(preparePaymentWithPRNRequest(utilityRequestData)),preparePaymentWithPRNRequest(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_PAYMENT",false);
                logger.info(responseData);
                return responseData;
            case "get_penalty_types":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(preparePenaltyTypeRequest(utilityRequestData)),preparePenaltyTypeRequest(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_PENALTYTYPE",false);
                logger.info(responseData);
                return responseData;
            case "select_penalty_type":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(selectPenaltyTypeRequest(utilityRequestData)),selectPenaltyTypeRequest(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_PENALTY",false);
                logger.info(responseData);
                return responseData;
            case "validate_customer_without_prn":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareValidationWithoutPRN(utilityRequestData)),prepareValidationWithoutPRN(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_VALIDATE",false);
                logger.info(responseData);
                return responseData;
            case "make_payment_without_prn":
                responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(preparePaymentWithoutPRN(utilityRequestData)),preparePaymentWithoutPRN(utilityRequestData).getData().getRequestId(),"PAYBILL_URA_PAYEMENT",false);
                logger.info(responseData);
                return responseData;
            default:
                return "Invalid menu selection";
        }
    }

    public BranchRequest prepareBranchRequest(UtilityRequestData utilityRequestData){
        logger.info("prepare branch request init...");
        BranchRequest branchRequest = new BranchRequest();
        BranchRequestData branchRequestData = new BranchRequestData();
        branchRequest.setUsername(API_USERNAME);
        branchRequest.setAction("PAYBILL_URA_BRANCH");
        branchRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        branchRequestData.setRequestId(utilityRequestData.getRequestId());
        branchRequest.setData(branchRequestData);
        logger.info(gson.toJson(branchRequest));
        return branchRequest;
    }

    public ValidateWithPRNRequest preparePRNValidationRequest(UtilityRequestData utilityRequestData){
        logger.info("PRN validation request init...");
        ValidateWithPRNRequest validateWithPRNRequest = new ValidateWithPRNRequest();
        ValidateWithPRNRequestData validateWithPRNRequestData = new ValidateWithPRNRequestData();
        validateWithPRNRequest.setAction("PAYBILL_URA_VALIDATE");
        validateWithPRNRequest.setUsername(API_USERNAME);
        validateWithPRNRequestData.setCustRef(utilityRequestData.getCustRef());
        validateWithPRNRequestData.setExtras(utilityRequestData.getExtras());
        validateWithPRNRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        validateWithPRNRequestData.setRequestId(utilityRequestData.getRequestId());
        validateWithPRNRequest.setData(validateWithPRNRequestData);
        logger.info(gson.toJson(validateWithPRNRequest));
        return validateWithPRNRequest;
    }

    public PaymentWithPRNRequest preparePaymentWithPRNRequest(UtilityRequestData utilityRequestData){
        logger.info("paymen with PRN init...");
        PaymentWithPRNRequest paymentWithPRNRequest = new PaymentWithPRNRequest();
        PaymentWithPRNRequestData paymentWithPRNRequestData = new PaymentWithPRNRequestData();
        paymentWithPRNRequest.setAction("PAYBILL_URA_PAYMENT");
        paymentWithPRNRequest.setUsername(API_USERNAME);
        paymentWithPRNRequestData.setAmount(utilityRequestData.getAmount());
        paymentWithPRNRequestData.setBranchId("001");
        paymentWithPRNRequestData.setCustRef(utilityRequestData.getCustRef());
        paymentWithPRNRequestData.setExtras(utilityRequestData.getExtras());
        paymentWithPRNRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        paymentWithPRNRequestData.setRequestId(utilityRequestData.getRequestId());
        paymentWithPRNRequestData.setTransId(utilityRequestData.getTransId());
        paymentWithPRNRequest.setData(paymentWithPRNRequestData);
        logger.info(gson.toJson(paymentWithPRNRequest));
        return paymentWithPRNRequest;
    }

    public PenaltyTypeRequest preparePenaltyTypeRequest(UtilityRequestData utilityRequestData){
        logger.info("penalty type request init...");
        PenaltyTypeRequest penaltyTypeRequest = new PenaltyTypeRequest();
        PenaltyTypeRequestData penaltyTypeRequestData = new PenaltyTypeRequestData();
        penaltyTypeRequest.setAction("PAYBILL_URA_PENALTYTYPE");
        penaltyTypeRequest.setUsername(API_USERNAME);
        penaltyTypeRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        penaltyTypeRequestData.setRequestId(utilityRequestData.getRequestId());
        penaltyTypeRequest.setData(penaltyTypeRequestData);
        logger.info(gson.toJson(penaltyTypeRequest));
        return penaltyTypeRequest;
    }

    public PayPenaltyRequest selectPenaltyTypeRequest(UtilityRequestData utilityRequestData){
        logger.info("penalty type request init....");
        PayPenaltyRequest payPenaltyRequest = new PayPenaltyRequest();
        PayPenaltyRequestData payPenaltyRequestData = new PayPenaltyRequestData();
        payPenaltyRequest.setAction("PAYBILL_URA_PENALTY");
        payPenaltyRequest.setUsername(API_USERNAME);
        payPenaltyRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        payPenaltyRequestData.setRequestId(utilityRequestData.getRequestId());
        payPenaltyRequest.setData(payPenaltyRequestData);
        logger.info(gson.toJson(payPenaltyRequest));
        return payPenaltyRequest;

    }

    public ValidationWithoutPRNRequest prepareValidationWithoutPRN(UtilityRequestData utilityRequestData){
        logger.info("validation without PRN init...");
        ValidationWithoutPRNRequest validationWithoutPRNRequest = new ValidationWithoutPRNRequest();
        ValidationWithoutPRNRequestData validationWithoutPRNRequestData = new ValidationWithoutPRNRequestData();
        validationWithoutPRNRequest.setAction("PAYBILL_URA_VALIDATE");
        validationWithoutPRNRequest.setUsername(API_USERNAME);
        validationWithoutPRNRequestData.setCustRef(utilityRequestData.getCustRef());
        validationWithoutPRNRequestData.setExtras(utilityRequestData.getExtras());
        validationWithoutPRNRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        validationWithoutPRNRequestData.setRequestId(utilityRequestData.getTransId());
        validationWithoutPRNRequest.setData(validationWithoutPRNRequestData);
        logger.info(gson.toJson(validationWithoutPRNRequest));
        return validationWithoutPRNRequest;
    }

    public PaymentWithoutPRNRequest preparePaymentWithoutPRN(UtilityRequestData utilityRequestData){
        logger.info("payment without PRN init....");
        PaymentWithoutPRNRequest paymentWithoutPRNRequest = new PaymentWithoutPRNRequest();
        PaymentWithoutPRNRequestData paymentWithoutPRNRequestData = new PaymentWithoutPRNRequestData();
        paymentWithoutPRNRequest.setAction("PAYBILL_URA_PAYEMENT");
        paymentWithoutPRNRequest.setUsername(API_USERNAME);
        paymentWithoutPRNRequestData.setAmount(utilityRequestData.getAmount());
        paymentWithoutPRNRequestData.setBranchId(utilityRequestData.getBranchId());
        paymentWithoutPRNRequestData.setCustRef(utilityRequestData.getCustRef());
        paymentWithoutPRNRequestData.setPhoneNo(utilityRequestData.getPhoneNo());
        paymentWithoutPRNRequestData.setRequestId(utilityRequestData.getRequestId());
        paymentWithoutPRNRequestData.setExtras(utilityRequestData.getExtras());
        paymentWithoutPRNRequestData.setTransId(utilityRequestData.getTransId());
        paymentWithoutPRNRequest.setData(paymentWithoutPRNRequestData);
        logger.info(gson.toJson(paymentWithoutPRNRequest));
        return paymentWithoutPRNRequest;
    }










}
