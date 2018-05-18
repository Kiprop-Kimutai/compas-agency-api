package compas.txn_params;

import compas.models.Transaction_Type;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public interface TransactionTypeRepository extends CrudRepository<Transaction_Type,Long> {
}
