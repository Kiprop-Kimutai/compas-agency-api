package compas.txn_params;

import compas.models.Transaction_Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Controller
public class TransactionTypeController {
    @Autowired(required = false)
    private TransactionTypeRepository transactionTypeRepository;
    public List<Transaction_Type> getAllTransactionypes(){
        return transactionTypeRepository.findAll();
    }
}
