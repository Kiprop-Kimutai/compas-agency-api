package compas.transaction;

import compas.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface TransactionRDBMSRepository extends CrudRepository<Transaction,Long> {
       //select cash in totals
    @Query("select COALESCE(sum(transaction.cash_in),0) from Transaction transaction where transaction.agent_id=:Id")
    Double selectCashInTotalsByAgentId(@Param("Id")Integer Id);

    @Query(value =  "select COALESCE(sum(transaction.cash_out),0)  from Transaction transaction where transaction.agent_id=:Id")
    Double  selectCashOutTotalsByAgentId(@Param("Id") Integer Id);

    @Query("update Transaction  transaction set transaction.status = 'I' where transaction.receipt_number = :receipt_number")
    Transaction updateAuthenticatedTransactionByReceiptNumber(@Param("receipt_number") String receipt_number);

}
