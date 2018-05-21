package compas.authentications;

import compas.agent.AgentRepository;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Agent;
import compas.models.Device;
import compas.models.Issued_Device;
import compas.transaction.TransactionRDBMSRepository;
import compas.txn_params.TransactionOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
@Controller
public class ValidateTransactions implements ValidateTransactionsInterface {
    private Logger logger = LoggerFactory.getLogger(ValidateTransactions.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;

    public Boolean authenticateDevice(Integer deviceId){
        logger.info("Authenticate device init..");
        try {
            List<Device> activeDevice = deviceRepository.findActiveDeviceById(deviceId);
            if(activeDevice.size() == 1){
                return true;
            }
            else{
                logger.info("AUTH FAILURE:::Device[%d] is INACTIVE or NOT registered");
                return false;
            }
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }

    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId){
        logger.info("Authenticate isued device init...");
        try{
            List<Issued_Device> issued_devices = issued_deviceRepository.findIssued_DeviceByDeviceIdAndAgentId(deviceId,agentId);
            if(issued_devices.size() > 0 ){
                logger.info("Found ::>>[%d]"+issued_devices.size());
                return true;
            }
            else{
                logger.info("AUTH FAILURE::: Device[%d] not issued to Agent[%d]",deviceId,agentId);
                return false;
            }
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }
    public Boolean authenticateAgent(Integer agentId){
        logger.info("authenticate agent init...");
        try {
            List<Agent> activeAgents = agentRepository.findActiveAgentById(1);
                activeAgents.forEach((agent -> {logger.info(agent.getAgent_description());}));
            if (activeAgents.size()>0) {
                return true;
            }
            else{
                logger.info("AUTH FAILURE::: AGENT[%d] inactive or NOT registered",agentId);
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public Boolean authenticateAccount(String account){
        logger.info("account length::"+account.length());
        logger.info("Authenticate account");
        //get string length of account number. Shoulf be 13 in length
        if( account.length() > 0 && account.length() != 13){
            logger.info("AUTH FAILURE:::Invalid account number Length");
            return false;
        }
        else{
            if(account.length() == 13) {
                if (account.matches("[0-9]+")) {
                    return true;
                }
                else{
                    logger.info("VALIDATION FAILURE::Invalid character memmber for account[%s]",account);
                    return false;
                }
            }
            else if (account.length() == 0){
                return true;
            }

        }
        return false;

    }
    public Boolean authenticateTransactionLimits(Double amount,Integer operationId,Integer agentId){
        logger.info("authenticate double limits");
        /*
         -determine whether operation is cash_in or cash_out
         -compare limits
         */
        try{
            Integer cash_flow_id = transactionOperationRepository.selectCashFlowId(operationId);
            if(cash_flow_id == 1){
                //compare limits
                if(agentRepository.findAgentDepositLimitsByAgentId(agentId) < transactionRDBMSRepository.selectCashInTotalsByAgentId(agentId)  + amount){
                    logger.info("AUTH FAILURE::CASH IN Transaction Limits for agent[%d] exceeded",agentId);
                    return false;
                }
                else{
                    return true;
                }
            }
            else if(cash_flow_id ==2){
                if((agentRepository.findAgentWithdrawalLimitsByAgentId(agentId) < (transactionRDBMSRepository.selectCashOutTotalsByAgentId(agentId))+0  + amount)){
                    logger.info("AUTH FAILURE::CASH OUT Transaction Limits for agent[%d] exceeded",agentId);
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                logger.info("AUTH FAILURE::Invalid cash_flow id[%D]",cash_flow_id);
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean authenticateGPSCoordinates(String longitude,String latitude){
        logger.info("Aythenticate o-ordinate");
        if(longitude!=null && latitude != null){
            return true;
        }
        else{
           logger.info("AUTH FAILED:::INVALID GPS co-ordinates");
        }
        return false;
    }
}
