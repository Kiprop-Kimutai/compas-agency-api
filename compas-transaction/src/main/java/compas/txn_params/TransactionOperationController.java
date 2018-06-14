package compas.txn_params;

import compas.models.Transaction_Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Controller
public class TransactionOperationController {
    @Autowired(required = false)
    private TransactionOperationsRepository transactionOperationsRepository;

    public List<Transaction_Operation> findAllTransactionOperations(){
        return transactionOperationsRepository.findAllOperations();
    }
}
