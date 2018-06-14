package compas.txn_params;

import compas.models.Transaction_Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Repository
public interface TransactionTypeRepository extends CrudRepository<Transaction_Type,Long> {
    public List<Transaction_Type> findAll();
}
