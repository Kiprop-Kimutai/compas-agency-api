package compas.transaction.passwordpolicy;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.OTP;
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
        query.addCriteria(Criteria.where("active").is(true));
        try{
            List<OTP> retrievedActiveOTPs = mongoOperations.find(query,OTP.class);
            return retrievedActiveOTPs;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public void updateOTPStatus(String password){
        Query query = new Query();
        query.addCriteria(Criteria.where("password").is(password));
        try{
            mongoOperations.updateFirst(query, Update.update("active",false),OTP.class);
        }
        catch (MongoException e){
            e.printStackTrace();
        }
    }
}
