package compas.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String serial_no;
    @Column(name = "mac_address")
    private String macAddress;
    private Boolean status;
    private Integer  created_by;
    //default constructor
    public Device(){}

    public Device(String serial_no, String macAddress, Boolean status, Integer created_by) {
        this.serial_no = serial_no;
        this.macAddress = macAddress;
        this.status = status;
        this.created_by = created_by;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public String getString(){
        return String.format("device[id = %d  serial = %s macaddress = %s status = %s created_by = %d]",Id,serial_no,macAddress,status,created_by);
    }
}
