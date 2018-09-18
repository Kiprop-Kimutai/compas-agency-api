package compas.authentications;

import compas.agent.AgentRepository;
import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.*;
import compas.transaction.TransactionRDBMSRepository;
import compas.txn_params.TransactionOperationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
@Controller
@EnableJpaRepositories("compas.models")
public class ValidateTransactions implements ValidateTransactionsInterface {
    private Logger logger = LoggerFactory.getLogger(ValidateTransactions.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private TransactionOperationsRepository transactionOperationsRepository;
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;

    public Boolean authenticateDevice(Integer deviceId){
        logger.info("Authenticating device ...");
        try {
            List<Device> activeDevice = deviceRepository.findActiveDeviceById(deviceId);
            if(activeDevice.size() == 1){
                activeDevice.forEach(active_device ->logger.info(active_device.getString()));
                return true;
            }
            else{
                logger.info(String.format("AUTH FAILURE:::device [%d] is INACTIVE or NOT registered"));
                return false;
            }
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }

    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId){
        logger.info("Authenticating issued device ...");
        try{
            List<Issued_Device> issued_devices = issued_deviceRepository.findIssued_DeviceByDeviceIdAndAgentId(deviceId,agentId);
            if(issued_devices.size() > 0 ){
                logger.info(String.format("Found %d devices",issued_devices.size()));
                issued_devices.forEach(issued_device ->logger.info(issued_device.getString()));
                return true;
            }
            else{
                logger.info(String.format("Authentication failure::: Device[%d] not issued to Agent1[%d]",deviceId,agentId));
                return false;
            }
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }
    public Boolean authenticateAgent(Integer agentId){
        logger.info("Authenticating agent...");
        try {
            List<Agent> activeAgents = agentRepository.findActiveAgentById(agentId);
                activeAgents.forEach((agent -> logger.info(String.format(agent.getString()))));
                logger.info(String.format("Found %d agents",activeAgents.size()));
            if (activeAgents.size()>0) {
                return true;
            }
            else{
                logger.info("AUTH FAILURE::: AGENT == %d  inactive or NOT registered",agentId);
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public Boolean authenticateAccount(String account){
        logger.info("Authenticating  account....");
        logger.info("account length::"+account.length());
        //get string length of account number. Should be 13 in length
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
                    logger.info(String.format("VALIDATION FAILURE::Invalid character member for account[%s]",account));
                    return false;
                }
            }
            else if (account.length() == 0){
                return true;
            }

        }
        return false;

    }
    public Boolean authenticateDailyTransactionLimits(Double amount,Integer operationId,Integer agentId){
        logger.info("Validate   limits ....");
        logger.info("Amount"+amount);
        logger.info("Operation ID"+operationId);
        logger.info("Agent1 ID"+agentId);
        /*
         -determine whether operation is cash_in or cash_out
         -compare limits
         */
        try{
            Integer cash_flow_id = transactionOperationsRepository.selectCashFlowId(operationId);
            logger.info("cash flow ID::"+cash_flow_id);
            if(cash_flow_id == 1){
                //compare limits
/*                if(agentRepository.findAgentDepositLimitsByAgentId(agentId) < transactionRDBMSRepository.selectCashInTotalsByAgentId(agentId)  + amount){
                    logger.info(String.format("VALIDATION FAILURE::CASH IN transactions limit for agent[%d] exceeded",agentId));
                    logger.info("false");
                    return false;
                }
                else{
                    logger.info("true");
                    return true;
                }*/
                return true;
            }
            else if(cash_flow_id ==2){
/*                if((agentRepository.findAgentWithdrawalLimitsByAgentId(agentId) < (transactionRDBMSRepository.selectCashOutTotalsByAgentId(agentId))  + amount)){
                    logger.info(String.format("AUTH FAILURE::CASH OUT Transactions Limits for agent[%d] exceeded",agentId));
                    logger.info("false");
                    return false;
                }
                else{
                    logger.info("true");
                    return true;
                }*/
                return true;
            }
            else{
                logger.info(String.format("AUTH FAILURE::Invalid cash_flow id[%d]",cash_flow_id));
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


    public Boolean authenticateCustomer(Customer customer){
        return true;
    }
    public Boolean authenticatePIN(Integer ModeId,String auth,String account){
        switch (ModeId){
            case 1:
                return processAuthentication(1,auth,account);
            case 2:
                return processAuthentication(2,auth,account);
            case 3:
                return processAuthentication(3,auth,account);

             }
        return false;
    }
    public Boolean processAuthentication(Integer mode,String authString,String account){
        if(mode == 1){
            //process PASS PIN to PIN validation server and Observe response
            return true;
        }
        else if(mode == 2){
            return true;
        }
        else if(mode == 3){
            return true;
        }
        return false;
    }

    public Boolean validateDailyTransactionLimit(Transactions transaction){
        logger.info("Validating daily transaction limit init ..");
        LocalDateTime fromDate,toDate,localDateTime = LocalDateTime.now();
        fromDate = localDateTime.withHour(0).withMinute(0).withSecond(0);
        toDate = localDateTime.withHour(23).withMinute(59).withSecond(59);
        Double totalDailyTransactions = 0.0;
        Double agentDailyTransactionLimit= agentRepository.agentDailyTransactionLimit(transaction.getAgent_id());
        totalDailyTransactions += transactionRDBMSRepository.totalDailyTransactions(fromDate.toString().replace("T"," "),toDate.toString().replace("T"," "),transaction.getAgent_id());
        logger.info("Total Daily transactions::"+totalDailyTransactions);
        //totalDailyTransactions += transactionRDBMSRepository.totalDailyTransactions(fromDate,toDate,transaction.getAgent_id());
        if(totalDailyTransactions >=agentDailyTransactionLimit){
            logger.info(String.format("AGENT %d HAS EXCEEDED DAILY TRANSACTION LIMIT",transaction.getAgent_id()));
            return false;
        }
        if(totalDailyTransactions +transaction.getAmount() >agentDailyTransactionLimit) {
            //logger.info(String.format("AGENT %d has %s daily transacting balance", +(agentDailyTransactionLimit - totalDailyTransactions)));
            return false;
        }

        return true;
    }

    public Boolean authenticateWeeklyTransactionLimits(Transactions transaction){
        logger.info("Validating weekly transaction limit init ..");
        //get day of the month from date
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime fromDate;
        LocalDateTime toDate;
        Double totalweeklytransactions =0.0;

        try{
            switch (classifyDaysIntoWeeks()){
                case 'a':
                    fromDate = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                    toDate = localDateTime.withDayOfMonth(7).withHour(23).withMinute(59).withSecond(59);
                    logger.info(fromDate.toString());
                    logger.info(toDate.toString());
                    //select sum(coalesce,0) from transactions where transactions.created_at between 'fromDate' and 'toDate'
                    totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate.toString().replace("T"," "),toDate.toString().replace("T"," "),transaction.getAgent_id());
                    //totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate,toDate,transaction.getAgent_id());
                    break;
                case 'b':
                     fromDate = localDateTime.withDayOfMonth(8).withHour(0).withMinute(0).withSecond(0);
                     toDate = localDateTime.withDayOfMonth(14).withHour(23).withMinute(59).withSecond(59);
                    //try converting SimpleDateFormat to yyyyMMdd
                    //select sum(coalesce,0) from  transactions where transactions.created_at between 'fromDate'
                    totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                    break;
                case 'c':
                    fromDate = localDateTime.withDayOfMonth(8).withHour(0).withMinute(0).withSecond(0);
                    toDate = localDateTime.withDayOfMonth(14).withHour(23).withMinute(59).withSecond(59);
                    totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                    break;
                case 'd':
                    fromDate = localDateTime.withDayOfMonth(8).withHour(0).withMinute(0).withSecond(0);
                    toDate = localDateTime.withDayOfMonth(14).withHour(23).withMinute(59).withSecond(59);
                    totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                    break;
                case 'e':
                    fromDate = localDateTime.withDayOfMonth(8).withHour(0).withMinute(0).withSecond(0);
                    toDate = localDateTime.withDayOfMonth(14).withHour(23).withMinute(59).withSecond(59);
                    totalweeklytransactions = transactionRDBMSRepository.totalWeeklyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        /*****QUERY FOR AGENT'S LIMITS*********/
        logger.info("Total weekly transactions::"+totalweeklytransactions);
        if(totalweeklytransactions>=agentRepository.agentWeeklyTransactionLimit(transaction.getAgent_id())){
            logger.info(String.format("AGENT %d HAS EXCEEDED WEEKLY TRANSACTION LIMIT",transaction.getAgent_id()));
            return false;
        }
        return true;
    }
    public Boolean authenticateMonthlyTransactionLimits(Transactions transaction){
        logger.info("Validating monthly transaction limit init ..");
        LocalDateTime fromDate,toDate,localDateTime = LocalDateTime.now();
        fromDate = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        Double totalMonthlyTransactions = 0.0;
        switch (getDaysOfMonth(localDateTime.getMonthValue())){
            case 'a':
                toDate = localDateTime.withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
                totalMonthlyTransactions = transactionRDBMSRepository.totalMonthlyTransactions(fromDate.toString().replace("T"," "),toDate.toString().replace("T"," "),transaction.getAgent_id());
                //totalMonthlyTransactions = transactionRDBMSRepository.totalMonthlyTransactions(fromDate,toDate,transaction.getAgent_id());
                break;
            case 'b':
                try{
                    toDate = localDateTime.withDayOfMonth(28).withHour(23).withMinute(59).withSecond(59);
                }
                catch(DateTimeException e){
                    toDate = localDateTime.withDayOfMonth(29).withHour(23).withMinute(59).withSecond(59);
                }
                totalMonthlyTransactions = transactionRDBMSRepository.totalMonthlyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                break;
            case 'c':
                toDate = localDateTime.withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59);
                totalMonthlyTransactions = transactionRDBMSRepository.totalMonthlyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                break;
        }
        logger.info("Total monthly transactions::"+totalMonthlyTransactions);
        if(totalMonthlyTransactions >= agentRepository.agentMonthlyTransactionLimit(transaction.getAgent_id())){
            logger.info(String.format("AGENT %d HAS EXCEEDED MONTHLY TRANSACTION LIMIT",transaction.getAgent_id()));
            return false;
        }
        return true;
    }
    public Boolean authenticateQuarterlyTransactionLimits(Transactions transaction){
        logger.info("Validating quarterly transaction limit init ..");
        LocalDateTime fromDate,toDate,localDateTime = LocalDateTime.now();
        Double totalQuarterlyTransactions = 0.0;
        Month month = localDateTime.getMonth();
        switch (clasifyDaysIntoMonths(month.getValue())){
            case 'a':
                fromDate = localDateTime.withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                toDate = localDateTime.withMonth(3).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
                totalQuarterlyTransactions = transactionRDBMSRepository.totalQuarterlyTransactions(fromDate.toString().replace("T"," "),toDate.toString().replace("T"," "),transaction.getAgent_id());
                //totalQuarterlyTransactions = transactionRDBMSRepository.totalQuarterlyTransactions(fromDate,toDate,transaction.getAgent_id());
                break;
            case 'b':
                fromDate = localDateTime.withMonth(4).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                toDate = localDateTime.withMonth(6).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59);
                totalQuarterlyTransactions = transactionRDBMSRepository.totalQuarterlyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                break;
            case 'c':
                fromDate = localDateTime.withMonth(7).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                toDate = localDateTime.withMonth(9).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59);
                totalQuarterlyTransactions = transactionRDBMSRepository.totalQuarterlyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
                break;
            case 'd':
                fromDate = localDateTime.withMonth(10).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                toDate = localDateTime.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
                totalQuarterlyTransactions = transactionRDBMSRepository.totalQuarterlyTransactions(fromDate.toString(),toDate.toString(),transaction.getAgent_id());
        }
        logger.info("Total quarterly transactions::"+totalQuarterlyTransactions);
        if(totalQuarterlyTransactions>=agentRepository.agentQuarterlyTransactionLimit(transaction.getAgent_id())){
            logger.info(String.format("AGENT %d HAS EXCEEDED QUARTERLY TRANSACTION LIMIT",transaction.getAgent_id()));
            return false;
        }
        return true;
    }

    public char classifyDaysIntoWeeks() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer day = localDateTime.getDayOfMonth();
        if(day>=1 && day <=7){
            return 'a';
        }
        else if(day>=8 && day <=14){
            return 'b';
        }
        else if(day>=15 && day <=21){
            return 'c';
        }
        else if(day>=22 && day <=28){
            return 'd';
        }
        else if(day>=29 && day <=31) {
            return 'e';
        }
        else {
            return 'e';
        }
    }

    public char clasifyDaysIntoMonths(Integer monthValue){
        if(monthValue >=0 && monthValue <=2){
            return 'a';
        }
        else if(monthValue >=3 && monthValue <=5){
            return 'b';
        }
        else if(monthValue >=6 && monthValue <=8){
            return 'c';
        }
        else{
            return 'd';
        }
    }

    public char getDaysOfMonth(Integer monthValue){
        logger.info("Month value::"+monthValue);
        if(monthValue == 1 || monthValue == 3 || monthValue == 5 || monthValue == 7 || monthValue == 8 || monthValue == 10 || monthValue == 12){
            return  'a';
        }
        if(monthValue == 2){
            return 'b';
        }
        else{
            return 'c';
        }
    }


    //@Credit LGPLv3.
    private double distanceFromBranch(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
    }
    //function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    //function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
