package compas.tariffs;

import compas.models.Tariff;
import compas.models.Transaction;
import compas.tariffs.TariffsRepository;
import compas.txn_params.TransactionOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Controller
public class TariffManager {
    @Autowired
    private TariffsRepository tariffsRepository;
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;
    private Logger logger = LoggerFactory.getLogger(TariffManager.class);

    public Transaction setTariffCharges(Transaction incomingTransaction){
        logger.info(incomingTransaction.getString());
        logger.info("Amount::"+incomingTransaction.getAmount());
        logger.info("Transaction Id::"+incomingTransaction.getOperational_id());
        Tariff applicableTariff = tariffsRepository.findTransactionTariffByTransaction_type_idAndAmount(transactionOperationRepository.findTransactionTypeIdByTransactionOperationId(incomingTransaction.getOperational_id()),incomingTransaction.getAmount());
        logger.info(""+applicableTariff.agent_income +" "+applicableTariff.getBank_income());
        incomingTransaction.setAgent_commision(((applicableTariff.getAgent_income())/100) *incomingTransaction.getAmount());
        incomingTransaction.setBank_income(((applicableTariff.getBank_income())/100)*incomingTransaction.getAmount());
        incomingTransaction.setExcise_duty((((applicableTariff.getExcise_duty())/100)*incomingTransaction.getAmount()));
        return incomingTransaction;
    }
}
