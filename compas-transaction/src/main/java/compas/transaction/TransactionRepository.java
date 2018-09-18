package compas.transaction;

import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.Transactions;
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

    public Transactions saveTransaction(Transactions transactionsRequest){
        logger.info("Repository init...");
        try{
            mongoOperations.save(transactionsRequest);
            return transactionsRequest;
        }
        catch(MongoException e){
            e.printStackTrace();
        }
        return new Transactions();
    }

    public List<Transactions> updateTransactionFlagWithMatchingReceipt(String receipt_number, String authentication){
        logger.info("insider....>>>>");
        logger.info(receipt_number+"password"+authentication);
        Query query = new Query();
        Transactions modelTransactions = new Transactions();
        modelTransactions.setReceipt_number(receipt_number);
        modelTransactions.setAuthenticatation(authentication);
        query.addCriteria(Criteria.where("receipt_number").is(receipt_number));
        try{
            mongoOperations.updateFirst(new Query(Criteria.where("receipt_number").is(modelTransactions.getReceipt_number())), Update.update("status","I"),Transactions.class);
            mongoOperations.updateFirst(query, Update.update("authentication",authentication),Transactions.class);
            List<Transactions> transactions = mongoOperations.find(new Query(Criteria.where("receipt_number").is(modelTransactions.getReceipt_number())),Transactions.class);
            return transactions;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Transactions updateProcessedTransaction(String receipt_number,String cbs_trans_id){
        try{
            mongoOperations.updateFirst(new Query(Criteria.where("receipt_number").is(receipt_number)),Update.update("status","S").addToSet("cbs_trans_id",cbs_trans_id),Transactions.class);
            List<Transactions> successFulTxns = mongoOperations.find(new Query(Criteria.where("receipt_number").is(receipt_number).and("status").is("S")),Transactions.class);
            return  successFulTxns.get(0);

        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new Transactions();
    }

}
