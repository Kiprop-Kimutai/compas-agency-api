package compas.txn_params;

import compas.models.Transaction_Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public interface  TransactionOperationRepository extends  CrudRepository<Transaction_Operation,Long> {
    @Query("select operation.transaction_type_id from Transaction_Operation operation where operation.Id = :Id")
    Integer findTransactionTypeIdById(@Param("Id") Integer Id);
}
