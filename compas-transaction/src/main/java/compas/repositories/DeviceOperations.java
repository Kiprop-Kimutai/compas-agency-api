package compas.repositories;

import compas.device.DeviceRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Device;
import compas.models.Issued_Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class DeviceOperations {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;

    public Device findByMacAddress(String mac_address){
        return deviceRepository.findByMacAddress(mac_address);
    }

    public List<Device> findActiveDeviceByMacAddress(String macAddress){
        return deviceRepository.findActiveDeviceByMacAddress(macAddress);
    }

    public List<Device> findActiveDeviceById( Integer Id){
        return deviceRepository.findActiveDeviceById(Id);
    }

    public Device findDeviceById( Integer Id){
        return deviceRepository.findDeviceById(Id);
    }

    public Issued_Device findByDeviceId(Integer deviceId){
        return issued_deviceRepository.findByDeviceId(deviceId);
    }

    public List<Issued_Device> findIssued_DeviceByDeviceIdAndAgentId(Integer deviceId, Integer agent_id){
        return issued_deviceRepository.findIssued_DeviceByDeviceIdAndAgentId(deviceId,agent_id);
    }

    public Integer findIssued_DeviceByAgent_id(Integer agent_id){
        return issued_deviceRepository.findIssued_DeviceByAgent_id(agent_id);
    }

    public List<Issued_Device> findIssued_DeviceByAgentId(Integer agent_id){
        return issued_deviceRepository.findIssued_DeviceByAgentId(agent_id);
    }

    public Issued_Device  findOneIssued_DeviceByAgent_id(Integer agent_id){
        return issued_deviceRepository.findOneIssued_DeviceByAgent_id(agent_id);
    }

}
