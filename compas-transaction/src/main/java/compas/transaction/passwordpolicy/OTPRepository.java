package compas.transaction.passwordpolicy;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.OTP;
import compas.models.Transaction;
import compas.transaction.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class OTPRepository  {
    ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperations = (MongoOperations) context.getBean("mongoTemplate");
    private Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    public OTP savePassword(OTP otp){
        try{
            mongoOperations.save(otp);
            return otp;
        }
        catch(MongoException e){
            e.printStackTrace();
        }
        return new OTP();
    }
    public List<OTP> fetchActiveOTPs(){
        Query query = new Query();
        query.addCriteria(Criteria.where("active").is(true).and("success").is(false));
        try{
            List<OTP> retrievedActiveOTPs = mongoOperations.find(query,OTP.class);
            return retrievedActiveOTPs;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<OTP> updateOTPStatus(String password){
        OTP placeHolderOTP = new OTP();
        Query query = new Query();
        placeHolderOTP.setPassword(password);
        query.addCriteria(Criteria.where("password").is(placeHolderOTP.getPassword()));
        try{
            mongoOperations.updateFirst(new Query(Criteria.where("password").is(placeHolderOTP.getPassword())), Update.update("active",false),OTP.class);
            mongoOperations.updateFirst(new Query(Criteria.where("password").is(placeHolderOTP.getPassword())), Update.update("success",true),OTP.class);
            List<OTP> updatedOTP = mongoOperations.find(new Query(Criteria.where("password").is(password)),OTP.class);
            return updatedOTP;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateUnusedOTPs(OTP otp){
        try{
            mongoOperations.updateFirst(new Query(Criteria.where("receipt_number").is(otp.getReceipt_number())),Update.update("success",false),OTP.class);
            mongoOperations.updateFirst(new Query(Criteria.where("receipt_number").is(otp.getReceipt_number())),Update.update("active",false),OTP.class);
        }
        catch (MongoException e){
            e.printStackTrace();
        }
    }
    public List<OTP> getSuccessfulOTPByReceiptNumber(String receipt_number){
        Query query= new Query();
        logger.info("MUTWOL");
        List<OTP> verifiedORUsedOTPS = new ArrayList<>();
        query.addCriteria(Criteria.where("active").is(false).and("success").is(true).and("receipt_number").is(receipt_number));
        try{
            logger.info("Init.... repo");
            logger.info("RECORD SIZE::"+mongoOperations.find(query,OTP.class).size());
            verifiedORUsedOTPS = mongoOperations.find(query,OTP.class);
            return verifiedORUsedOTPS;
        }
        catch(MongoException e){
            e.printStackTrace();
            //return verifiedORUsedOTPS;
        }
        return  verifiedORUsedOTPS;
    }

}
