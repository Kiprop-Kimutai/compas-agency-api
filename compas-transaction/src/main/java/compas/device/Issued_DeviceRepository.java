package compas.device;

import compas.models.Issued_Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public interface Issued_DeviceRepository extends CrudRepository<Issued_Device,Long> {

    public List<Issued_Device> findByCreatedBy(String created_by);
    public Issued_Device findByDeviceId(Integer deviceId);

    @Query("select issued_device from Issued_Device issued_device where issued_device.deviceId = :deviceId and issued_device.agent_id = :agent_id")
    public List<Issued_Device> findIssued_DeviceByDeviceIdAndAgentId(@Param("deviceId") Integer deviceId,@Param("agent_id") Integer agent_id);

}
