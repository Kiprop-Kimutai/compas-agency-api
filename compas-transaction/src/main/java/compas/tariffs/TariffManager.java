package compas.tariffs;

import com.google.gson.Gson;
import compas.agent.AgentRepository;
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
    @Autowired
    private AgentRepository agentRepository;
    private Logger logger = LoggerFactory.getLogger(TariffManager.class);
    private Tariff applicableTariff = new Tariff();
    private Tariff fallBackTariff = new Tariff();
    List<Transaction_Charge>  undefined_transaction_charges = new ArrayList<>();

    public Transactions setTariffCharges(Transactions incomingTransactions){
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
        logger.info("this way out...");
        return setRespectiveIncomes(incomingTransaction,charge_amount);
    }

    public Transactions setRespectiveIncomes(Transactions incomingTransaction,Double charge_amount){
        Gson gson = new Gson();
        logger.info("init...");
        Tariff applicableTariff = tariffsRepository.findTariffbyTransactionOperationId(incomingTransaction.getOperational_id());
        logger.info("applicable::"+gson.toJson(applicableTariff));
        Preferential_Tariff preferred_tariff = AgentIsPreferredForTransactionOperation(incomingTransaction);
        Integer charge_burden = transaction_charge_repository.findTransaction_ChargeBurdenByTransaction_operation_id(incomingTransaction.getOperational_id());
        logger.info("Burden:"+charge_burden);
        logger.info("************PREFERRED TARIFF*********"+gson.toJson(preferred_tariff));
        Agent agent = agentRepository.findById(incomingTransaction.getAgent_id());
        /**IF AGENT IS PREFERRED,calculate preferential rates here **/
        if(preferred_tariff.getAgent_id()!=0){
            //incomingTransaction.setAgent_commision((charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100);
            if(agent.getPsp_id()>0){
                incomingTransaction.setAgent_commision(((100-applicableTariff.getPsp_portion())*(charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100)/100);
                incomingTransaction.setPsp_income(applicableTariff.getPsp_portion()*((charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100)/100);
            }
            else{
                incomingTransaction.setAgent_commision((charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100);
            }
            /*incomingTransaction.setAgent_commision(((100-applicableTariff.getPsp_portion())*(charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100)/100);
            incomingTransaction.setPsp_income(applicableTariff.getPsp_portion()*((charge_amount * (applicableTariff.getAgent_portion()+preferred_tariff.getFavoured_percentage()))/100)/100);*/
            incomingTransaction.setBank_income((charge_amount * (applicableTariff.getBank_portion()-preferred_tariff.getFavoured_percentage()))/100);
            incomingTransaction.setExcise_duty((applicableTariff.getExcise_duty()*incomingTransaction.getBank_income())/100);
            logger.info("Excise duty::"+incomingTransaction.getExcise_duty());
        }
        else{
            //incomingTransaction.setAgent_commision((charge_amount * applicableTariff.getAgent_portion())/100);
            incomingTransaction.setBank_income((charge_amount * applicableTariff.getBank_portion())/100);
            incomingTransaction.setExcise_duty((applicableTariff.getExcise_duty()*incomingTransaction.getBank_income())/100);
            if(agent.getPsp_id()>0){
                incomingTransaction.setAgent_commision((100-applicableTariff.getPsp_portion())*((charge_amount * applicableTariff.getAgent_portion())/100)/100);
                incomingTransaction.setPsp_income((applicableTariff.getPsp_portion())*((charge_amount * applicableTariff.getAgent_portion())/100)/100);

            }
            else{
                incomingTransaction.setAgent_commision((charge_amount * applicableTariff.getAgent_portion())/100);
            }
            logger.info("Excise duty::"+incomingTransaction.getExcise_duty());
        }
        if(charge_burden ==1){
            //do nothing. customer bears charge burden
        }
        else if(charge_burden ==2){
            Double newAgentCommission,newPspIncome = 0.0;
            if(agent.getPsp_id()>0){
                 newAgentCommission = (100-applicableTariff.getPsp_portion()*(incomingTransaction.getAgent_commision()+incomingTransaction.getBank_income())/100);
                newPspIncome = (applicableTariff.getPsp_portion() *(incomingTransaction.getAgent_commision()+incomingTransaction.getBank_income())/100);

            }
            else{
                 newAgentCommission = incomingTransaction.getAgent_commision()+incomingTransaction.getBank_income();
                 newPspIncome = 0.0;
            }
            //Double newAgentCommission = incomingTransaction.getAgent_commision()+incomingTransaction.getBank_income();
            Double newBankIncome = (-1*incomingTransaction.getBank_income());
            incomingTransaction.setAgent_commision(newAgentCommission);
            incomingTransaction.setPsp_income(newPspIncome);
            incomingTransaction.setBank_income(newBankIncome);
            incomingTransaction.setExcise_duty(0.0);
        }
        return incomingTransaction;
    }

    public Preferential_Tariff AgentIsPreferredForTransactionOperation(Transactions transaction){
        logger.info("checking preferential tariff......");
        return  preferredTariffRepository.findPreferredTariffByAgentIdAndTransactionOperation(transaction.getAgent_id(),transaction.getOperational_id())!=null ?preferredTariffRepository.findPreferredTariffByAgentIdAndTransactionOperation(transaction.getAgent_id(),transaction.getOperational_id()) :new Preferential_Tariff(0,"0",0,0.0);

    }
}
