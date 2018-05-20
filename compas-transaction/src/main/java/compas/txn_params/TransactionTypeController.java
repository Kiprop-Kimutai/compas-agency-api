package compas.txn_params;

import compas.models.Transaction_Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class TransactionTypeController {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    public List<Transaction_Type> getAllTransactionypes(){
        return transactionTypeRepository.findAll();
    }
}
