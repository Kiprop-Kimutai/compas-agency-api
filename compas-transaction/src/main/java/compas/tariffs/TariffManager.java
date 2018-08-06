package compas.tariffs;

import com.google.gson.Gson;
import compas.models.*;
import compas.txn_params.TransactionOperationsRepository;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Controller
public class TariffManager {
    @Autowired
    private TariffsRepository tariffsRepository;
    @Autowired
    private TransactionOperationsRepository transactionOperationsRepository;
    @Autowired
    private Transaction_Charge_Repository transaction_charge_repository;
    @Autowired
    private Transaction_Charge_Types_Repository transaction_charge_types_repository;
    @Autowired
    private PreferredTariffRepository preferredTariffRepository;
    private Logger logger = LoggerFactory.getLogger(TariffManager.class);
    private Tariff applicableTariff = new Tariff();
    private Tariff fallBackTariff = new Tariff();
     List<Transaction_Charge>  undefined_transaction_charges = new ArrayList<>();
    //undefined_transaction_charges.add(new Transaction_Charge(0,0,0.00,0.0,0.0));

    public Transactions setTariffCharges(Transactions incomingTransactions){
/*        fallBackTariff.setAgent_income(0.0);
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
        incomingTransactions.setExcise_duty((((applicableTariff.getExcise_duty())/100)* incomingTransactions.getAmount()));*/
        return incomingTransactions;
    }

    public Transactions setCharges(Transactions incomingTransaction){
        Double charge_amount;
        Transaction_Charge transaction_charge;
        undefined_transaction_charges.add(new Transaction_Charge(0,0,0.00,0.0,0.0));
        /**DETERMINE TRANSACTION CHARGE TYPE **/
        List<Transaction_Charge> transaction_charges= transaction_charge_repository.findTransaction_ChargeById(incomingTransaction.getOperational_id()) !=null ?  transaction_charge_repository.findTransaction_ChargeById(incomingTransaction.getOperational_id()):undefined_transaction_charges;
        if(transaction_charges.size()>1){
            transaction_charge = transaction_charge_repository.findTransaction_ChargeByLimitsAndTransaction_operation_id(incomingTransaction.getAmount().doubleValue(),incomingTransaction.getOperational_id());
        }
        else if(transaction_charges.size() ==1){
            transaction_charge = transaction_charges.get(0);
        }
        else{
            transaction_charge = undefined_transaction_charges.get(0);
        }
        Transaction_Charge_Types transaction_charge_type = transaction_charge_types_repository.findTransaction_Charge_TypesById(transaction_charge.getCharge_type_id()) != null ? transaction_charge_types_repository.findTransaction_Charge_TypesById(transaction_charge.getCharge_type_id()) : new Transaction_Charge_Types("");
        /***CALCULATE TRANSACTION CHARGE HERE*****/
        //Transaction_Charge transaction_charge = transaction_charges.get(0);
        switch(transaction_charge_type.getName().toLowerCase()){
            case "percentage":
                charge_amount = (transaction_charge.getCharge()*incomingTransaction.getAmount())/100;
                break;
            case "fixed":
                charge_amount = transaction_charge.getCharge();
                break;
            case "variable":
                charge_amount = transaction_charge_repository.findVariableTransaction_ChargeByTransactionOperationAndAmount(incomingTransaction.getAmount().doubleValue(),incomingTransaction.getOperational_id());
                break;
                default:
                    charge_amount = 0.0;
                    break;
        }

        /**SET RESPECTIVE SHARES OF TRANSACTION_CHARGE and return the processed transaction**/
        return setRespectiveIncomes(incomingTransaction,charge_amount);
    }

    public Transactions setRespectiveIncomes(Transactions incomingTransaction,Double charge_amount){
        Gson gson = new Gson();
        Tariff applicableTariff = tariffsRepository.findTariffbyTransactionOperationId(incomingTransaction.getOperational_id());
        Preferential_Tariff preferred_tariff = AgentIsPreferredForTransactionOperation(incomingTransaction);
        logger.info("************PREFERRED TARIFF*********"+gson.toJson(preferred_tariff));
            /**IF AGENT IS PREFERRED,calculate preferential rates here **/
        if(preferred_tariff.getAgent_id()!=0){
            incomingTransaction.setAgent_commision((charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100);
            incomingTransaction.setBank_income((charge_amount * (applicableTariff.getBank_portion()-preferred_tariff.getFavoured_percentage()))/100);
            incomingTransaction.setExcise_duty((applicableTariff.getExcise_duty()*incomingTransaction.getBank_income())/100);
            logger.info("Excise duty::"+incomingTransaction.getExcise_duty());
        }
        else{
            incomingTransaction.setAgent_commision((charge_amount * applicableTariff.getAgent_portion())/100);
            incomingTransaction.setBank_income((charge_amount * applicableTariff.getBank_portion())/100);
            incomingTransaction.setExcise_duty((applicableTariff.getExcise_duty()*incomingTransaction.getBank_income())/100);
            logger.info("Excise duty::"+incomingTransaction.getExcise_duty());
        }
        return incomingTransaction;
    }

    public Preferential_Tariff AgentIsPreferredForTransactionOperation(Transactions transaction){
        logger.info("checking preferntial tariff......");
        return  preferredTariffRepository.findPreferredTariffByAgentIdAndTransactionOperation(transaction.getAgent_id(),transaction.getOperational_id())!=null ?preferredTariffRepository.findPreferredTariffByAgentIdAndTransactionOperation(transaction.getAgent_id(),transaction.getOperational_id()) :new Preferential_Tariff(0,"0",0,0.0);

    }
}
