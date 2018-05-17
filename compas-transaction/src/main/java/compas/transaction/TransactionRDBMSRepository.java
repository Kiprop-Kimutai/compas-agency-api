package compas.transaction;

import compas.models.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by CLLSDJACKT013 on 15/05/2018.
 */
public interface TransactionRDBMSRepository extends CrudRepository<Transaction,Long> {

}
