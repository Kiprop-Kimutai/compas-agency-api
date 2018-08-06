package compas.authentications;

import compas.models.Customer;
import compas.models.Transactions;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface ValidateTransactionsInterface {
    public Boolean authenticateDevice(Integer deviceId);
    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId);
    public Boolean authenticateAgent(Integer agentId);
    public Boolean authenticateAccount(String account);
    public Boolean authenticateDailyTransactionLimits(Double amount,Integer operationId,Integer agentId);
    public Boolean validateDailyTransactionLimit(Transactions transaction);
    public Boolean authenticateWeeklyTransactionLimits(Transactions transaction);
    public Boolean authenticateMonthlyTransactionLimits(Transactions transaction);
    public Boolean authenticateQuarterlyTransactionLimits(Transactions transaction);
    public Boolean authenticateGPSCoordinates(String longitude,String latitude);
    public Boolean authenticateCustomer(Customer customer);
    public Boolean authenticatePIN(Integer ModeId,String auth,String account);
}

