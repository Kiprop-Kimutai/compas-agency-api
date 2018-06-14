package compas.customer;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.CustomerRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
