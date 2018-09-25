package compas.utilities;

import com.google.gson.Gson;
import compas.models.utilities.UtilityRequest;
import compas.models.utilities.UtilityRequestData;
import compas.models.utilities.umeme.UmemePaymentData;
import compas.models.utilities.umeme.UmemePaymentRequest;
import compas.models.utilities.umeme.UmemeValidateData;
import compas.models.utilities.umeme.UmemeValidateRequest;
import compas.restservice.RestServiceConfig;
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
    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(UtilitiesController.class);
    @ResponseBody
    @RequestMapping(path="/billrequest",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity processBillPaymentRequest(@RequestBody String billRequest){
        UtilityRequest utilityRequest = gson.fromJson(billRequest,UtilityRequest.class);
        return ResponseEntity.status(201).body("ok..");
    }

    public String processBills(UtilityRequest utilityRequest){
        switch(utilityRequest.getUtility().toUpperCase()){
            case "UMEME":
                return processUmemeBills(utilityRequest.getMenu(),utilityRequest.getTransactionbody());
            case "NWSC":
                break;
            case "URA":
                break;
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

        }
        return "";
    }

    public String processUmemeBills(String menu,UtilityRequestData requestData){
        String responseData = "";
        switch (menu){
            case "validate_customer":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareUmemeCustomerValidation(requestData)),prepareUmemeCustomerValidation(requestData).getData().getRequestId(),"PAYBILL_UMEME_VALIDATE");
                 logger.info(responseData);
                 return responseData;
            case "pay_bill":
                 responseData =  restServiceConfiguration.RestServiceConfiguration(protocol,SERVICE_IP,SERVICE_PORT,SERVICE_ENDPOINT,"POST",gson.toJson(prepareUmemeCustomerValidation(requestData)),prepareUmemeCustomerValidation(requestData).getData().getRequestId(),"PAYBILL_UMEME_VALIDATE");
                 logger.info(responseData);
                 return responseData;
            default:
                return "";
        }
    }

    public UmemeValidateRequest prepareUmemeCustomerValidation(UtilityRequestData requestData){
        UmemeValidateRequest umemeValidateRequest = new UmemeValidateRequest();
        UmemeValidateData validateData = new UmemeValidateData();
        umemeValidateRequest.setUsername(API_USERNAME);
        umemeValidateRequest.setAction("PAYBILL_UMEME_VALIDATE");
        validateData.setBranchId("0001");
        validateData.setCustRef(requestData.getCustRef());
        validateData.setPhoneNo(requestData.getPhoneNo());
        validateData.setRequestId(requestData.getDevice_transaction_id());
        umemeValidateRequest.setData(validateData);
        //save utilities request data
        utilitiesRequestDataRepository.save(requestData);
        return umemeValidateRequest;

    }

    public UmemePaymentRequest prepareUmemePaymentRequest(UtilityRequestData requestData){
        UmemePaymentRequest umemePaymentRequest = new UmemePaymentRequest();
        UmemePaymentData umemePaymentData = new UmemePaymentData();
        umemePaymentRequest.setUsername(API_USERNAME);
        umemePaymentRequest.setAction("PAYBILL_UMEME_PAYMENT");
        umemePaymentData.setBranchId("001");
        umemePaymentData.setCustRef(requestData.getCustRef());
        umemePaymentData.setPhoneNo(requestData.getPhoneNo());
        umemePaymentData.setAmount(requestData.getAmount());
        umemePaymentData.setRequestId("");
        umemePaymentData.setTransId("");
        umemePaymentData.setTransrefNo("");
        umemePaymentRequest.setData(umemePaymentData);
        //save umeme payment data
        utilitiesRequestDataRepository.save(requestData);
        return umemePaymentRequest;

    }




}
