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
    @Column()
    private String customer_id_number;
    @Column(unique = true)
    private String rt;
    @Column(unique = true)
    private String rt_raw;
    @Column(unique = true)
    private String ri;
    @Column(unique = true)
    private String ri_raw;
    @Column(unique=true)
    private String  rm;
    @Column(unique=true)
    private String  rm_raw;
    @Column(unique=true)
    private String rr;
    @Column(unique=true)
    private String rr_raw;
    @Column(unique=true)
    private String rs;
    @Column(unique=true)
    private String rs_raw;
    @Column(unique = true)
    private String lt;
    @Column(unique=true)
    private String lt_raw;
    @Column(unique = true)
    private String li;
    @Column(unique = true)
    private String li_raw;
    @Column(unique = true)
    private String lm;
    @Column(unique = true)
    private String lm_raw;
    @Column(unique = true)
    private String lr;
    @Column(unique = true)
    private String lr_raw;
    @Column(unique = true)
    private String ls;
    @Column(unique = true)
    private String ls_raw;
    @Column(unique = true)
    private String photo;
    @Column(unique = true)
    private String signature;
    @Column(unique = true)
    private String scanned_id;

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

    public String getRt_raw() {
        return rt_raw;
    }

    public void setRt_raw(String rt_raw) {
        this.rt_raw = rt_raw;
    }

    public String getRi_raw() {
        return ri_raw;
    }

    public void setRi_raw(String ri_raw) {
        this.ri_raw = ri_raw;
    }

    public String getRm_raw() {
        return rm_raw;
    }

    public void setRm_raw(String rm_raw) {
        this.rm_raw = rm_raw;
    }

    public String getRr_raw() {
        return rr_raw;
    }

    public void setRr_raw(String rr_raw) {
        this.rr_raw = rr_raw;
    }

    public String getRs_raw() {
        return rs_raw;
    }

    public void setRs_raw(String rs_raw) {
        this.rs_raw = rs_raw;
    }

    public String getLt_raw() {
        return lt_raw;
    }

    public void setLt_raw(String lt_raw) {
        this.lt_raw = lt_raw;
    }

    public String getLi_raw() {
        return li_raw;
    }

    public void setLi_raw(String li_raw) {
        this.li_raw = li_raw;
    }

    public String getLm_raw() {
        return lm_raw;
    }

    public void setLm_raw(String lm_raw) {
        this.lm_raw = lm_raw;
    }

    public String getLr_raw() {
        return lr_raw;
    }

    public void setLr_raw(String lr_raw) {
        this.lr_raw = lr_raw;
    }

    public String getLs_raw() {
        return ls_raw;
    }

    public void setLs_raw(String ls_raw) {
        this.ls_raw = ls_raw;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getScanned_id() {
        return scanned_id;
    }

    public void setScanned_id(String scanned_id) {
        this.scanned_id = scanned_id;
    }

    public String getString(){
        return String.format("FP biodata[RT[%s] LT[%s]  RS[%s] LS[%s]]",rt,lt,rs,ls);
    }
}
