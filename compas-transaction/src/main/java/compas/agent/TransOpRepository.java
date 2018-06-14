package compas.agent;

import compas.models.Transaction_Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 06/06/2018.
 */
@Repository
public interface TransOpRepository extends CrudRepository<Transaction_Operation,Long> {
    @Query("select '*' from Transaction_Operation ")
    List<Transaction_Operation> findAllOperations();

    List<Transaction_Operation> findAll();

    @Query("select operation.transaction_type_id from Transaction_Operation operation where operation.Id = :Id")
    Integer findTransactionTypeIdByTransactionOperationId(@Param("Id") Integer Id);


    @Query("select trans_operation.cash_flow_id from Transaction_Operation  trans_operation where trans_operation.id = :Id")
    Integer selectCashFlowId(@Param("Id")Integer Id);

    @Query("select transaction_operation from Transaction_Operation transaction_operation where transaction_operation.id =:Id")
    Transaction_Operation findTransaction_OperationById(@Param("Id")Integer Id);
}
