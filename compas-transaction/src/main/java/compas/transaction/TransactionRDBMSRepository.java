package compas.transaction;

import compas.models.Transactions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface TransactionRDBMSRepository extends CrudRepository<Transactions,Long> {
       //select cash in totals
/*    @Query("select COALESCE(sum(transactions.cash_in),0) from Transactions transactions where transactions.agent_id=:Id")
    Double selectCashInTotalsByAgentId(@Param("Id")Integer Id);

    @Query(value =  "select COALESCE(sum(transactions.cash_out),0)  from Transactions transactions where transactions.agent_id=:Id")
    Double  selectCashOutTotalsByAgentId(@Param("Id") Integer Id);*/

    @Modifying
    @Query("update Transactions  transactions set transactions.status = 'I' where transactions.receipt_number = :receipt_number")
    void updateAuthenticatedTransactionsByReceiptNumber(@Param("receipt_number") String receipt_number);
    @Modifying
    @Query("update Transactions transactions set transactions.status = 'S' , transactions.cbs_trans_id = :cbs_trans_id,transactions.finnacle_response_message = :finnacle_response_message where transactions.receipt_number = :receipt_number")
    int  updateProcessedTransactionsByReceiptNumber(@Param("receipt_number") String receipt_number, @Param("cbs_trans_id") String cbs_trans_id,@Param("finnacle_response_message")String finnacle_response_message);

    @Modifying
    @Query("update Transactions transactions set transactions.status ='F',transactions.finnacle_response_message = :finnacle_response_message where transactions.receipt_number =:receipt_number")
    int updateFailedTransactions(@Param("receipt_number") String receipt_number,@Param("finnacle_response_message")String finnacle_response_message);

    @Query(value = "select COALESCE(sum(transactions.amount),0) from Transactions  transactions where transactions.status = 'S' and transactions.agent_id = :agent_id and transactions.transaction_date between :fromDate and :toDate")
    Double totalDailyTransactions(@Param("fromDate")String fromDate, @Param("toDate")String toDate, @Param("agent_id")Integer agent_id);

    @Query( value = "select COALESCE(sum(transaction.amount),0) from Transactions  transaction where transaction.status = 'S' and transaction.agent_id = :agent_id and  transaction.transaction_date between :fromDate and :toDate ")
    Double totalWeeklyTransactions(@Param("fromDate")String fromDate,@Param("toDate")String toDate,@Param("agent_id")Integer agent_id);

    @Query(value = "select COALESCE(sum(transactions.amount),0) from Transactions  transactions where transactions.status = 'S' and transactions.agent_id = :agent_id and transactions.transaction_date between :fromDate and :toDate")
    Double totalMonthlyTransactions(@Param("fromDate")String fromDate, @Param("toDate")String toDate, @Param("agent_id")Integer agent_id);

    @Query(value = "select COALESCE(sum(transaction.amount),0) from Transactions transaction where transaction.status = 'S' and  transaction.agent_id = :agent_id and transaction.transaction_date between :fromDate and :toDate")
    Double totalQuarterlyTransactions(@Param("fromDate") String fromDate,@Param("toDate")String toDate,@Param("agent_id")Integer agent_id);

    @Query("select transaction from Transactions transaction where  transaction.terminal_trans_id = :terminal_trans_id")
    Transactions findTimeOutTransactionByTerminal_trans_id(@Param("terminal_trans_id")String terminal_trans_id);

    @Modifying
    @Query("update Transactions transaction set transaction.status = 'R' where transaction.terminal_trans_id = :terminal_trans_id")
    int updateReversedTransaction(@Param("terminal_trans_id")String terminal_trans_id);
}
