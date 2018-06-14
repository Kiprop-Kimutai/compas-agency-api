package compas.repositories;

import compas.models.Transaction_Mode;
import compas.models.Transaction_Operation;
import compas.models.Transaction_Type;
import compas.models.Transactions;
import compas.models.bankoperations.Inquiries.InquiriesRequest;
import compas.models.bankoperations.Inquiries.InquiriesRequestData;
import compas.transaction.TransactionRDBMSRepository;
import compas.transaction.TransactionRepository;
import compas.transactionschannel.TransactionOperationRepository;
import compas.txn_params.InquiriesRequestDataRepository;
import compas.txn_params.TransactionModeRepository;
import compas.txn_params.TransactionOperationsRepository;
import compas.txn_params.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class TransactionOperations {

    private TransactionRepository transactionRepository = new TransactionRepository();
    @Autowired
    private TransactionRDBMSRepository transactionRDBMSRepository;
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;
    @Autowired
    private TransactionModeRepository transactionModeRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Autowired
    private InquiriesRequestDataRepository inquiriesRequestDataRepository;
    @Autowired
    private TransactionOperationsRepository transactionOperationsRepository;



    public Transactions saveTransaction(Transactions transactionsRequest){
        return  transactionRepository.saveTransaction(transactionsRequest);
    }

    public InquiriesRequestData saveInquiriesData(InquiriesRequestData inquiriesRequestData){
        return inquiriesRequestDataRepository.save(inquiriesRequestData);
    }

    public List<Transactions> updateTransactionFlagWithMatchingReceipt(String receipt_number, String authentication){
        return  transactionRepository.updateTransactionFlagWithMatchingReceipt(receipt_number,authentication);
    }

    public Transactions updateProcessedTransaction(String receipt_number){
        return transactionRepository.updateProcessedTransaction(receipt_number);
    }

    public Double selectCashInTotalsByAgentId(Integer Id){
        return transactionRDBMSRepository.selectCashInTotalsByAgentId(Id);
    }

    public Double  selectCashOutTotalsByAgentId(Integer Id){
        return transactionRDBMSRepository.selectCashOutTotalsByAgentId(Id);
    }

    public void updateAuthenticatedTransactionsByReceiptNumber(String receipt_number){
        transactionRDBMSRepository.updateAuthenticatedTransactionsByReceiptNumber(receipt_number);
    }

    public void updateProcessedTransactionsByReceiptNumber(String receipt_number){
         transactionRDBMSRepository.updateProcessedTransactionsByReceiptNumber(receipt_number);
    }

    public Transaction_Operation findTransaction_OperationById(Integer Id){
        return transactionOperationRepository.findTransaction_OperationById(Id);
    }

    public Integer selectCashFlowId(Integer Id){
        return transactionOperationRepository.selectCashFlowId(Id);
    }

    public  List<Transaction_Operation> findAll(){
        return  transactionOperationRepository.findAll();
    }

    public List<Transaction_Mode> findAllTransctionModes(){
        return  transactionModeRepository.findAll();
    }

    public List<Transaction_Operation> findAllOperations(){
        return transactionOperationsRepository.findAllOperations();
    }

    public  List<Transaction_Operation> findAllTransactionOperations(){
        return transactionOperationsRepository.findAll();
    }
    public Integer findTransactionTypeIdByTransactionOperationId(Integer Id){
        return transactionOperationsRepository.findTransactionTypeIdByTransactionOperationId(Id);
    }

    public  Integer selectTransactionOperationCashFlowId(Integer Id){
        return transactionOperationsRepository.selectCashFlowId(Id);
    }

    public Transaction_Operation findTransactionOperationById(Integer Id){
        return transactionOperationsRepository.findTransaction_OperationById(Id);
    }
    public List<Transaction_Type> findAllTransactionTypes(){
        return transactionTypeRepository.findAll();
    }

    public void updateSuccessfulInquiryRequestData(String TransId){
        inquiriesRequestDataRepository.updateSuccessfulInquiryRequestData(TransId);
    }
















}
