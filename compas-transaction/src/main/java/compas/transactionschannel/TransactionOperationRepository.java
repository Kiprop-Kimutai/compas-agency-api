package compas.transactionschannel;

import compas.models.Transaction_Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 31/05/2018.
 */
public interface TransactionOperationRepository  extends CrudRepository<Transaction_Operation,Long>{
    @Query("select transaction_operation from Transaction_Operation transaction_operation where transaction_operation.id =:Id")
    Transaction_Operation findById(@Param("Id")Integer Id);
}
