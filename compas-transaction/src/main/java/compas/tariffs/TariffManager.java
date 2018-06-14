package compas.tariffs;

import compas.models.Tariff;
import compas.models.Transactions;
import compas.txn_params.TransactionOperationsRepository;
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
    private TransactionOperationsRepository transactionOperationsRepository;
    private Logger logger = LoggerFactory.getLogger(TariffManager.class);
    private Tariff applicableTariff = new Tariff();
    private Tariff fallBackTariff = new Tariff();


    public Transactions setTariffCharges(Transactions incomingTransactions){
        fallBackTariff.setAgent_income(0.0);
        fallBackTariff.setBank_income(0.0);
        fallBackTariff.setExcise_duty(0.0);
        logger.info(incomingTransactions.getString());
        logger.info("Amount::"+ incomingTransactions.getAmount());
        logger.info("Transactions Id::"+ incomingTransactions.getOperational_id());
        //Tariff applicableTariff = tariffsRepository.findTransactionTariffByTransaction_type_idAndAmount(transactionOperationsRepository.findTransactionTypeIdByTransactionOperationId(incomingTransactions.getOperational_id()), incomingTransactions.getAmount());
         applicableTariff = tariffsRepository.findTransactionTariffByOperation_idAndAmount(incomingTransactions.getOperational_id(), incomingTransactions.getAmount()+0.0) != null ? tariffsRepository.findTransactionTariffByOperation_idAndAmount(incomingTransactions.getOperational_id(), incomingTransactions.getAmount()+0.0) : fallBackTariff;
        logger.info(""+applicableTariff.agent_income +" "+applicableTariff.getBank_income());
        incomingTransactions.setAgent_commision(((applicableTariff.getAgent_income())/100) * incomingTransactions.getAmount());
        incomingTransactions.setBank_income(((applicableTariff.getBank_income())/100)* incomingTransactions.getAmount());
        incomingTransactions.setExcise_duty((((applicableTariff.getExcise_duty())/100)* incomingTransactions.getAmount()));
        return incomingTransactions;
    }
}
