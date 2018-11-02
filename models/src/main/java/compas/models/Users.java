package compas.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String	username;
    private String first_name;
    private String surname;
    private String other_names;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String group_id;
    private String  agent_code;
    private Boolean status;
    private Integer created_by;
    private Integer  type_id;
    private Integer pos_user_level;
    private Integer verified_by;
    private String verified;
    private Boolean locked;
    private Boolean password_expired;
    private Boolean firstlogin;

    //defult constructor
    public Users(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOther_names() {
        return other_names;
    }

    public void setOther_names(String other_names) {
        this.other_names = other_names;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
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

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getPos_user_level() {
        return pos_user_level;
    }

    public void setPos_user_levels(Integer pos_user_level) {
        this.pos_user_level = pos_user_level;
    }

    public void setPos_user_level(Integer pos_user_level) {
        this.pos_user_level = pos_user_level;
    }

    public Integer getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(Integer verified_by) {
        this.verified_by = verified_by;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getPassword_expired() {
        return password_expired;
    }

    public void setPassword_expired(Boolean password_expired) {
        this.password_expired = password_expired;
    }

    public Boolean getFirstlogin() {
        return firstlogin;
    }

    public void setFirstlogin(Boolean firstlogin) {
        this.firstlogin = firstlogin;
    }

    public String getString(){
        return String.format("user[username = %s  surname = %s email= %s phone = %s]",username,surname,email,phone);
    }
}
