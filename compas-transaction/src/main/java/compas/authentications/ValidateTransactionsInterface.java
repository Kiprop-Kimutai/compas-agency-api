package compas.authentications;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface ValidateTransactionsInterface {
    public Boolean authenticateDevice(Integer deviceId);
    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId);
    public Boolean authenticateAgent(Integer agentId);
    public Boolean authenticateAccount(String account);
    public Boolean authenticateTransactionLimits(Double amount,Integer operationId,Integer agentId);
    public Boolean authenticateGPSCoordinates(String longitude,String latitude);
}

