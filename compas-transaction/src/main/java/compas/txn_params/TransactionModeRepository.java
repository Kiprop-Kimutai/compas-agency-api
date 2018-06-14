package compas.txn_params;

import compas.models.Transaction_Mode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 18/05/2018.
 */
@Repository
public interface TransactionModeRepository extends CrudRepository<Transaction_Mode,Long>{
    List<Transaction_Mode> findAll();
}
