package compas.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
@Entity
public class ExampleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer Id;
    public String username;
    public String othernames;
    public String mail;
    public String password;
    public Integer roleId;

    //default constrcutor
    public ExampleUser(){}

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public String toString(){
        return String.format("user[username = %s othernames = %s password = %s roledId = %d mail = %s]",username,othernames,password,roleId,mail);
    }
}
