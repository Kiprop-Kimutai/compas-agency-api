package compas.models;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
@Entity
public class Issued_Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(name = "device_id")
    private Integer deviceId;
    private Integer agent_id;
    private Integer branch_id;
    @Column(name = "created_by")
    private Integer  createdBy;
    //default constructor
    public Issued_Device(){}

    public Issued_Device(Integer deviceId, Integer agent_id, Integer branch_id, Integer createdBy) {
        this.deviceId = deviceId;
        this.agent_id = agent_id;
        this.branch_id = branch_id;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        Id = Id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
        this.agent_id = agent_id;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreated_by(Integer created_by) {
        this.createdBy = created_by;
    }



    public String getString(){
        return String.format("issued_device[id = %d device_id = %d agent_id = %d branch = %d created by = %d]",Id,deviceId,agent_id,branch_id,createdBy);
    }
}
