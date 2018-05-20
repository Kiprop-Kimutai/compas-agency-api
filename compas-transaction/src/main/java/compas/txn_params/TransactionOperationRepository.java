package compas.txn_params;

import compas.models.Transaction_Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public interface  TransactionOperationRepository extends  CrudRepository<Transaction_Operation,Long> {

    List<Transaction_Operation> findAll();
    @Query("select operation.transaction_type_id from Transaction_Operation operation where operation.Id = :Id")
    Integer findTransactionTypeIdByTransactionOperationId(@Param("Id") Integer Id);


    @Query("select trans_operation.cash_flow_id from Transaction_Operation  trans_operation where trans_operation.id = :Id")
    Integer selectCashFlowId(@Param("Id")Integer Id);
}
