package compas.device;

import com.google.gson.Gson;
import compas.models.Device;
import compas.models.Issued_Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path = "/device")
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(Device.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Issued_DeviceRepository issuedDeviceRepository;
    private Gson gson = new Gson();

    @RequestMapping(path = "/saveDevice")
    @ResponseBody
    public ResponseEntity addDevice(@RequestBody String deviceString){
        Device deviceToSave = gson.fromJson(deviceString,Device.class);
        Device savedDevice = deviceRepository.save(deviceToSave);
        return ResponseEntity.status(201).body(savedDevice);
    }

    @RequestMapping(path = "/fetchDevice")
    @ResponseBody
    public ResponseEntity getDevice(@RequestBody String requestString){
        Device device = gson.fromJson(requestString,Device.class);
        logger.info(device.getMacAddress());
        /*
        -extract device details from deviceRepository using  mac address
        -fetch issuedDevice details using device_id above
         */
        return ResponseEntity.status(201).body(device.getMacAddress());
    }

    @RequestMapping(path = "/issueDevice",method = RequestMethod.POST,produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity issueDevice(@RequestBody String issueDevice){
        //Issued_Device issued_device = gson.fromJson(issueDevice,Issued_Device.class);
        /*
        -required:-authenticate device before issuing.
        -checkpoints::is device active, is device issued?,is agent active?, is branch active?
         */
        Issued_Device issued_device = issuedDeviceRepository.save(gson.fromJson(issueDevice,Issued_Device.class));
        return ResponseEntity.status(201).body(issued_device);
    }

}
