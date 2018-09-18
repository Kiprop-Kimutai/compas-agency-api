package compas.txn_params;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.bankoperations.Inquiries.InquiriesRequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Created by CLLSDJACKT013 on 8/24/2018.
 */
public class InquiriesRequestDataMongoRepository {
    ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperations = (MongoOperations) context.getBean("mongoTemplate");
    private Logger logger = LoggerFactory.getLogger(InquiriesRequestDataMongoRepository.class);

    public InquiriesRequestData saveInquiriesRequest(InquiriesRequestData inquiriesRequestData){
        logger.info("Repository init...");
        try{
            mongoOperations.save(inquiriesRequestData);
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new InquiriesRequestData();
    }
}
