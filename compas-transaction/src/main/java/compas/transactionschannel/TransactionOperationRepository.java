package compas.transactionschannel;

import compas.models.Transaction_Operation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 31/05/2018.
 */
@Repository
public interface TransactionOperationRepository  extends CrudRepository<Transaction_Operation,Long>{
    @Query("select transaction_operation from Transaction_Operation transaction_operation where transaction_operation.id =:Id")
    Transaction_Operation findTransaction_OperationById(@Param("Id")Integer Id);

    @Query("select trans_operation.cash_flow_id from Transaction_Operation  trans_operation where trans_operation.id = :Id")
    Integer selectCashFlowId(@Param("Id")Integer Id);

    List<Transaction_Operation> findAll();

}
