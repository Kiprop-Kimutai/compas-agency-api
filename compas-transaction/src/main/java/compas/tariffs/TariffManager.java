package compas.tariffs;

import compas.models.Tariff;
import compas.models.Transaction;
import compas.tariffs.TariffsRepository;
import compas.txn_params.TransactionOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class TariffManager {
    @Autowired
    private TariffsRepository tariffsRepository;
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;

    public Transaction setTariffCharges(Transaction incomingTransaction){
        Tariff applicableTariff = tariffsRepository.findTransactionTariffByTransaction_type_idAndAmount(transactionOperationRepository.findTransactionTypeIdById(incomingTransaction.getId()),incomingTransaction.getAmount());
        incomingTransaction.setAgent_commision(((applicableTariff.getAgent_income())/100) *incomingTransaction.getAmount());
        incomingTransaction.setBank_income(((applicableTariff.getBank_income())/100)*incomingTransaction.getAmount());
        incomingTransaction.setExcise_duty((((applicableTariff.getExcise_duty())/100)*incomingTransaction.getAmount()));
        return incomingTransaction;
    }
}
