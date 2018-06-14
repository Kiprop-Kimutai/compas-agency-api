package compas.device;

import compas.models.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device,Long> {
     public List<Device> findByStatus(Boolean status);
     public Device findByMacAddress(String mac_address);


     @Query("select d from Device d  where d.macAddress = :macAddress and d.status = true")
     public List<Device> findActiveDeviceByMacAddress(@Param("macAddress") String macAddress);

     @Query("select device from Device device where device.id = :Id and device.status = true")
     public List<Device> findActiveDeviceById(@Param("Id") Integer Id);

     @Query("select device from Device device where device.id = :Id")
     Device findDeviceById(@Param("Id") Integer Id);
}
