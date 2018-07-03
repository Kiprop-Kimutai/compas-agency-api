package compas.transaction;

import compas.models.Transactions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface TransactionRDBMSRepository extends CrudRepository<Transactions,Long> {
       //select cash in totals
    @Query("select COALESCE(sum(transactions.cash_in),0) from Transactions transactions where transactions.agent_id=:Id")
    Double selectCashInTotalsByAgentId(@Param("Id")Integer Id);

    @Query(value =  "select COALESCE(sum(transactions.cash_out),0)  from Transactions transactions where transactions.agent_id=:Id")
    Double  selectCashOutTotalsByAgentId(@Param("Id") Integer Id);

    @Modifying
    @Query("update Transactions  transactions set transactions.status = 'I' where transactions.receipt_number = :receipt_number")
    void updateAuthenticatedTransactionsByReceiptNumber(@Param("receipt_number") String receipt_number);
    @Modifying
    @Query("update Transactions transactions set transactions.status = 'S' , transactions.cbs_trans_id = :cbs_trans_id where transactions.receipt_number = :receipt_number")
    int  updateProcessedTransactionsByReceiptNumber(@Param("receipt_number") String receipt_number, @Param("cbs_trans_id") String cbs_trans_id);

    @Modifying
    @Query("update Transactions transactions set transactions.status ='F' where transactions.receipt_number =:receipt_number")
    int updateFailedTransactions(@Param("receipt_number") String receipt_number);
}
