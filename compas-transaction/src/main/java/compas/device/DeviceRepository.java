package compas.device;

import compas.models.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public interface DeviceRepository extends CrudRepository<Device,Long> {
     public List<Device> findByStatus(Boolean status);
     public Device findByMacAddress(String mac_address);

     @Query("select d from Device d  where d.macAddress = :macAddress and d.status = active")
     public List<Device> findActiveDeviceByMacAddress(@Param("macAddress") String macAddress);
}
