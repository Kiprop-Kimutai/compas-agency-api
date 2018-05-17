package compas.transaction;

import com.google.gson.Gson;
import com.mongodb.Mongo;
import compas.models.Transaction;
import compas.restservice.RestServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@RestController
@RequestMapping(path="/api/compas/pbu/agency")
public class TransactionController {
    private Gson gson = new Gson();
    @Autowired
    private RestServiceConfig restServiceConfiguration;
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;
    private TransactionRepository transactionRepository = new TransactionRepository();
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @RequestMapping(path="/test",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity test(){
        return ResponseEntity.ok("end point up...");
    }

    @RequestMapping(path="/post_transaction",method=RequestMethod.POST,produces = "application/json")
    public ResponseEntity postTransaction(@RequestBody String transactionString){
        logger.info(transactionString);
        Transaction transaction = gson.fromJson(transactionString,Transaction.class);
        logger.info("Transaction Request::"+transaction);
        //authenticate transaction
        //carry out repository operations
        transactionRepository.saveTransaction(transaction);
        Transaction returnTx = transactionRDBMSRepository.save(transaction);
        //forward received transaction to CBS for processing
        Transaction response = gson.fromJson(restServiceConfiguration.RestServiceConfiguration("/api/mockTransactions","POST",gson.toJson(returnTx)),Transaction.class);
        return ResponseEntity.status(201).body(returnTx);
    }

}
