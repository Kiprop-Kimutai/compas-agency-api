package compas.authentication;

import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Device;
import compas.models.Issued_Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public class TransactionsAuthentication  implements TransactionsAuthInterface {
    private Logger logger = LoggerFactory.getLogger(TransactionsAuthentication.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    public Boolean authenticateDevice(Device device){
        try {
            List<Device> activeDevice = deviceRepository.findActiveDeviceByMacAddress(device.getMacAddress());
            if(activeDevice.size()>0){
                return true;
            }

        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }
    public Boolean authenticateIssuedDevice(Integer deviceId,Integer agentId){
        try{
            List<Issued_Device> issued_devices = issued_deviceRepository.findIssued_DeviceByDeviceIdAndAgentId(deviceId,agentId);
            if(issued_devices.size() ==1 ){
                return true;
            }

        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }
    public Boolean authenticateAgent(){
        return true;
    }
    public Boolean authenticateAccount(){
        return true;
    }
    public Boolean authenticateLimits(){
        return true;
    }
    public Boolean authenticateGPSCoordinates(String longitude,String latitude){
        return false;
    }
}
