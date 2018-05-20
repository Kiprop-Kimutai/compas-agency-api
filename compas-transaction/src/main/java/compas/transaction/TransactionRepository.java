package compas.transaction;

import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;


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

    public List<Transaction> updateTransactionFlagWithMatchingReceipt(String receipt_number,String authentication){
        Query query = new Query();
        Transaction modelTransaction = new Transaction();
        modelTransaction.setReceipt_number(receipt_number);
        modelTransaction.setAuthenticatation(authentication);
        query.addCriteria(Criteria.where("receipt_number").is(receipt_number));
        try{
            mongoOperations.updateFirst(new Query(Criteria.where("receipt_number").is(modelTransaction.getReceipt_number())), Update.update("status","I"),Transaction.class);
            mongoOperations.updateFirst(query, Update.update("authentication",authentication),Transaction.class);
            List<Transaction> transaction = mongoOperations.find(new Query(Criteria.where("receipt_number").is(modelTransaction.getReceipt_number())),Transaction.class);
            return transaction;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
