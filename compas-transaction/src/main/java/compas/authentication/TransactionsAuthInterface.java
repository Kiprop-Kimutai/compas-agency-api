package compas.authentication;

import compas.models.Device;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface TransactionsAuthInterface {
    public Boolean authenticateDevice(Device device);
    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId);
    public Boolean authenticateAgent(Integer agentId);
    //public Boolean authenticateAccount();
    public Boolean authenticateTransactionLimits(Integer operationId);
    public Boolean authenticateGPSCoordinates(String longitude,String latitude);
}

