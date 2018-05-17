package compas.transaction;

import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
public class TransactionRepository {
    ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperations = (MongoOperations) context.getBean("mongoTemplate");
    private Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    public Transaction saveTransaction(Transaction transactionRequest){
        logger.info("Repository init...");
        try{
            mongoOperations.save(transactionRequest);
            return transactionRequest;
        }
        catch(MongoException e){
            e.printStackTrace();
        }
        return new Transaction();
    }

}
