package compas.models;

import javax.persistence.*;

/**
 * Created by CLLSDJACKT013 on 20/05/2018.
 */
@Entity
public class Fingerprints {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(nullable = false)
    private String customer_id_number;
    @Column(nullable = false)
    private String rt;
    @Column(nullable = false)
    private String ri;
    @Column(nullable = false)
    private String  rm;
    @Column(nullable = false)
    private String rr;
    @Column(nullable = false)
    private String rs;
    @Column(nullable = false)
    private String lt;
    @Column(nullable = false)
    private String li;
    @Column(nullable = false)
    private String lm;
    @Column(nullable = false)
    private String lr;
    @Column(nullable = false)
    private String ls;
    @Column(nullable = false)
    private String photo;

    //default constructor for JPA
    public Fingerprints(){}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCustomer_id_number() {
        return customer_id_number;
    }

    public void setCustomer_id_number(String customer_id_number) {
        this.customer_id_number = customer_id_number;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRi() {
        return ri;
    }

    public void setRi(String ri) {
        this.ri = ri;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getLi() {
        return li;
    }

    public void setLi(String li) {
        this.li = li;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getString(){
        return String.format("FP biodata[RT[%s] LT[%s]  RS[%s] LS[%s]]",rt,lt,rs,ls);
    }
}
