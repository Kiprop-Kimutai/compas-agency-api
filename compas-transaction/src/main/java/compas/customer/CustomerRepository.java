package compas.customer;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.AccountBioRequest;
import compas.models.CustomerRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by CLLSDJACKT013 on 24/05/2018.
 */
@Repository
public class CustomerRepository {
    ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperations = (MongoOperations) context.getBean("mongoTemplate");

    public void saveCustomer(CustomerRequest customerRequest){
        try{
            mongoOperations.save(customerRequest);
        }
        catch(MongoException e){
            e.printStackTrace();
        }

    }

    public CustomerRequest getCustomerRequestByAccountNumber(AccountBioRequest accountBioRequest){

        org.springframework.data.mongodb.core.query.Query query_by_account_number,query_by_account_name = new org.springframework.data.mongodb.core.query.Query();
        query_by_account_number = query_by_account_name;
        query_by_account_number.addCriteria(Criteria.where("account_number").is(accountBioRequest.getAccount_number()));
        query_by_account_name.addCriteria(Criteria.where("account_name").is(accountBioRequest.getAccount_name()));

        try{
                if(accountBioRequest.getAccount_name()!=null){
                    mongoOperations.findOne(new Query(Criteria.where("account_name").is(accountBioRequest.getAccount_name())),CustomerRequest.class);
                }
                else if(accountBioRequest.getAccount_number() !=null){
                    mongoOperations.findOne(new Query(Criteria.where("account_number").is(accountBioRequest.getAccount_number())),CustomerRequest.class);
                }
                else{
                    //return new CustomerRequest();
                }
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new CustomerRequest();
    }
}
