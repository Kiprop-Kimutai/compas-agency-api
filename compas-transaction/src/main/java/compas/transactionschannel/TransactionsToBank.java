package compas.transactionschannel;

import com.google.gson.Gson;
import compas.accounts.AccountsRepository;
import compas.agent.AgentRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.SMS;
import compas.models.Transactions;
import compas.models.Transaction_Operation;
import compas.models.bankoperations.AccountInquiry.ACRequestData;
import compas.models.bankoperations.AccountInquiry.AccountInquiryRequest;
import compas.models.bankoperations.Inquiries.*;
import compas.models.bankoperations.deposit.DepositRequest;
import compas.models.bankoperations.deposit.DepositRequestData;
import compas.models.bankoperations.mobilemoney.Bank2WalletRequest;
import compas.models.bankoperations.mobilemoney.Bank2WalletRequestData;
import compas.models.bankoperations.reversals.ReversalRequest;
import compas.models.bankoperations.reversals.ReversalRequestData;
import compas.models.bankoperations.sms.SmsRequest;
import compas.models.bankoperations.transfers.TransferRequest;
import compas.models.bankoperations.transfers.TransferRequestData;
import compas.models.bankoperations.withdrawal.WithdrawalRequest;
import compas.models.bankoperations.withdrawal.WithdrawalRequestData;
import compas.txn_params.TransactionOperationsRepository;
import compas.utilities.UtilitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 31/05/2018.
 */
@Configuration
public class TransactionsToBank {
    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(TransactionsToBank.class);
    private InquiriesRequestData inquiriesRequestData = new InquiriesRequestData();
    private InquiriesResponseData inquiriesResponseData = new InquiriesResponseData();
    private InquiriesRequest inquiriesRequest = new InquiriesRequest();
    private InquiriesResponse inquiriesResponse = new InquiriesResponse();
    TransferRequestData transferRequestData = new TransferRequestData();
    TransferRequest transferRequest = new TransferRequest();
    BatchAccountBalanceInquiry batchAccountBalanceInquiry= new BatchAccountBalanceInquiry();
    BatchAccountBalanceInquiryData batchAccountBalanceInquiryData = new BatchAccountBalanceInquiryData();
    @Autowired
    private TransactionOperationsRepository transactionOperationRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private UtilitiesRepository utilitiesRepository;

    public String prepareTransactionsToBank(Transactions transactions, String Username) {
        logger.info(gson.toJson(transactions));
        //guery action from transactions operation for posted transactions
        logger.info("Operational ID::" + transactions.getOperational_id());
        logger.info("AGENT ID::"+transactions.getAgent_id());
        logger.info("Cash flow id" + transactionOperationRepository.selectCashFlowId(transactions.getOperational_id()));
        //transactionOperationRepository.findTransaction_OperationById(transactions.getOperational_id());
        Transaction_Operation transaction_operation = transactionOperationRepository.findTransaction_OperationById(transactions.getOperational_id());
        String [] transaction_charges = {transactions.getBank_income().toString(),transactions.getAgent_commision().toString(),transactions.getExcise_duty().toString()};
        //try switch here

        switch (transaction_operation.getAction().toUpperCase()) {
            case "BAL":
                logger.info("ACCOUNT NAME HERE::"+transactions.getCustomer_name());
                inquiriesRequestData.setTransId(transactions.getReceipt_number());
                inquiriesRequestData.setNarration(transaction_operation.getName());
                inquiriesRequestData.setAccount(transactions.getAccount_from());
                inquiriesRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                inquiriesRequestData.setTellerId(transactions.getAgent_id().toString());
                inquiriesRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                inquiriesRequestData.setCustomerName("OKURA RONALD");
                //inquiriesRequestData.setCustomerName(transactions.getCustomer_name());
                //inquiriesRequestData.setCharges(transaction_charges);
                //BUILD FINAL Inquiries data
                inquiriesRequest.setUsername(Username);
                inquiriesRequest.setAction(transaction_operation.getAction());
                inquiriesRequest.setData(inquiriesRequestData);
                return gson.toJson(inquiriesRequest);
            case "MINI":
                inquiriesRequestData.setTransId(transactions.getReceipt_number());
                inquiriesRequestData.setNarration(transaction_operation.getName());
                inquiriesRequestData.setAccount(transactions.getAccount_from());
                inquiriesRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                inquiriesRequestData.setTellerId(transactions.getAgent_id().toString());
                inquiriesRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                //inquiriesRequestData.setCustomerName("Martin Chirchir");
                inquiriesRequestData.setCustomerName(transactions.getCustomer_name());
                inquiriesRequestData.setCharges(transaction_charges);
                //BUILD FINAL REQUEST
                inquiriesRequest.setUsername(Username);
                inquiriesRequest.setAction(transaction_operation.getAction());
                inquiriesRequest.setData(inquiriesRequestData);
                return gson.toJson(inquiriesRequest);
            case "BAL_LIST":
                batchAccountBalanceInquiryData.setRequestId(transactions.getReceipt_number());
                batchAccountBalanceInquiryData.setAccountsList(transactions.getAccounts_list());
                batchAccountBalanceInquiryData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()));
                batchAccountBalanceInquiryData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                batchAccountBalanceInquiryData.setTellerId(transactions.getAgent_id().toString());
                //BUILD FINAL REQUEST
                batchAccountBalanceInquiry.setUsername(Username);
                batchAccountBalanceInquiry.setAction(transaction_operation.getAction());
                batchAccountBalanceInquiry.setData(batchAccountBalanceInquiryData);
                return gson.toJson(batchAccountBalanceInquiry);

            case "TRANSFER":
                transferRequestData.setTransId(transactions.getReceipt_number());
                transferRequestData.setNarration(transaction_operation.getName());
                transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                transferRequestData.setTellerId(transactions.getAgent_id().toString());
                transferRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                //transferRequestData.setCustomerName("Diana Machora");
                transferRequestData.setCustomerName(transactions.getCustomer_name());
                transferRequestData.setFromAccount(transactions.getAccount_from());
                transferRequestData.setToAccount(transactions.getAccount_to());
                transferRequestData.setAmount(transactions.getAmount().toString());
                transferRequestData.setCharges(transaction_charges);
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setData(transferRequestData);
                return gson.toJson(transferRequest);

            case "REVERSAL":   //NO CHARGES
                ReversalRequestData reversalRequestData = new ReversalRequestData();
                ReversalRequest reversalRequest = new ReversalRequest();
                reversalRequestData.setTransId(transactions.getReceipt_number());
                reversalRequestData.setNarration(transaction_operation.getName());
                reversalRequestData.setAccount(transactions.getAccount_from());
                reversalRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                reversalRequestData.setTellerId(transactions.getAgent_id().toString());
                reversalRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                reversalRequestData.setCustomerName(transactions.getCustomer_name());
                reversalRequestData.setOldTransId(transactions.getCbs_trans_id());
                reversalRequestData.setAmount(transactions.getAmount().toString());
                //BUILD FINAL REQUEST HERE
                reversalRequest.setUsername(Username);
                reversalRequest.setAction(transaction_operation.getAction());
                reversalRequest.setData(reversalRequestData);
                return gson.toJson(reversalRequestData);

            case "DEPOSIT":
                //TransferRequestData transferRequestData= new TransferRequestData();
                //TransferRequest transferRequest = new TransferRequest();
                transferRequestData.setTransId(transactions.getReceipt_number());
                transferRequestData.setNarration(transaction_operation.getName());
                transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                transferRequestData.setTellerId(transactions.getAgent_id().toString());
                transferRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                //transferRequestData.setCustomerName("Diana Machora");
                transferRequestData.setCustomerName(transactions.getCustomer_name());
                transferRequestData.setToAccount(transactions.getAccount_to());
                transferRequestData.setFromAccount(accountsRepository.findAccountByIdNumber(agentRepository.findById(transactions.getAgent_id()).getAgent_code()));
                transferRequestData.setAmount(transactions.getAmount().toString());
                transferRequestData.setCharges(transaction_charges);
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                //transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setAction("TRANSFER");
                transferRequest.setData(transferRequestData);
                return gson.toJson(transferRequest);

            case "WITHDRAW":
                transferRequestData.setTransId(transactions.getReceipt_number());
                transferRequestData.setNarration(transaction_operation.getName());
                transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                transferRequestData.setTellerId(transactions.getAgent_id().toString());
                //transferRequestData.setTellerId("");
                transferRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                //transferRequestData.setCustomerName("Jonah Hexx");
                transferRequestData.setCustomerName(transactions.getCustomer_name());
                transferRequestData.setFromAccount(transactions.getAccount_from());
                transferRequestData.setToAccount(accountsRepository.findAccountByIdNumber(agentRepository.findById(transactions.getAgent_id()).getAgent_code()));
                transferRequestData.setAmount(transactions.getAmount().toString());
                transferRequestData.setCharges(transaction_charges);
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                //transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setAction("TRANSFER");
                transferRequest.setData(transferRequestData);
                return gson.toJson(transferRequest);

            case "ACCT_INQUIRY":   //NO CHARGES HERE
                AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
                ACRequestData acRequestData = new ACRequestData();
                acRequestData.setAccount(transactions.getAccount_from());
                acRequestData.setRequestId(transactions.getReceipt_number());
                //Integer deviceId = issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId();
                if(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id())!=null){
                    acRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                }
                else{
                    acRequestData.setDeviceId("1");
                }
                //acRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString()!=null ? issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString():"1");
                acRequestData.setTellerId(transactions.getAgent_id().toString());
                //acRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                acRequestData.setBranchId("0001");
                //BUILD FINAL REQUEST HERE
                accountInquiryRequest.setAction(transaction_operation.getAction());
                //accountInquiryRequest.setAction("ACCT_INQUIRY");
                accountInquiryRequest.setUsername(Username);
                accountInquiryRequest.setData(acRequestData);
                logger.info(gson.toJson(accountInquiryRequest));
                return gson.toJson(accountInquiryRequest);

            case "UTILITIES":
                transferRequestData.setTransId(transactions.getReceipt_number());
                transferRequestData.setNarration(utilitiesRepository.findUtilityByUtility_code(transactions.getUtility_code()) + " " +transaction_operation.getName());
                transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                transferRequestData.setTellerId(transactions.getAgent_id().toString());
                //transferRequestData.setTellerId("");
                transferRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                //transferRequestData.setCustomerName("Jonah Hexx");
                transferRequestData.setCustomerName(transactions.getCustomer_name());
                transferRequestData.setFromAccount(transactions.getAccount_from());
                transferRequestData.setToAccount(transactions.getAccount_to());
                transferRequestData.setReferenceAccount(transactions.getReference_account());
                transferRequestData.setAmount(transactions.getAmount().toString());
                transferRequestData.setCharges(transaction_charges);
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                //transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setAction("TRANSFER");
                transferRequest.setData(transferRequestData);

                return gson.toJson(transferRequest);

            case "OPENED_ACCTS":
                inquiriesRequestData.setFrom(transactions.getFrom());
                inquiriesRequestData.setTo(transactions.getTo());
                inquiriesRequestData.setSchemeCode(transactions.getSchemeCode());
                //Build FINAL Request here
                inquiriesRequest.setUsername(Username);
                inquiriesRequest.setAction("OPENED_ACCTS");
                inquiriesRequest.setData(inquiriesRequestData);
                return  gson.toJson(inquiriesRequest);

            case "TRANS_INQUIRY":
                inquiriesRequestData.setTransId(transactions.getOriginal_transId());
                //BUILD FINAL REQUEST HERE
                inquiriesRequest.setUsername(Username);
                inquiriesRequest.setAction("TRANS_INQUIRY");
                inquiriesRequest.setData(inquiriesRequestData);
                return gson.toJson(inquiriesRequest);

            case "BANK2WALLET":
                Bank2WalletRequest bank2WalletRequest = new Bank2WalletRequest();
                Bank2WalletRequestData bank2WalletRequestData = new Bank2WalletRequestData();
                bank2WalletRequestData.setAmount(transactions.getAmount().toString());
                bank2WalletRequestData.setTransrefNo(transactions.getReceipt_number());
                bank2WalletRequestData.setNarration("Bank to Wallet");
                bank2WalletRequestData.setPhoneNo(transactions.getPhone());
                //BUILD final request here
                bank2WalletRequest.setAction("BANK2WALLET");
                bank2WalletRequest.setData(bank2WalletRequestData);
                bank2WalletRequest.setUsername(Username);
                return gson.toJson(bank2WalletRequest);

            case "AIRTIME":
                Bank2WalletRequest airtimeRequest = new Bank2WalletRequest();
                Bank2WalletRequestData airtimeRequestData = new Bank2WalletRequestData();
                airtimeRequestData.setAmount(transactions.getAmount().toString());
                airtimeRequestData.setPhoneNo(transactions.getPhone());
                airtimeRequestData.setNarration("Airtime Purchase");
                airtimeRequestData.setTransrefNo(transactions.getReceipt_number());
                //Build final request here
                airtimeRequest.setUsername(Username);
                airtimeRequest.setAction("AIRTIME");
                airtimeRequest.setData(airtimeRequestData);
                return gson.toJson(airtimeRequest);

            case "BAL_LISTT":
                BatchAccountBalanceInquiry batchAccountBalanceInquiry = new BatchAccountBalanceInquiry();
                BatchAccountBalanceInquiryData batchAccountBalanceInquiryData = new BatchAccountBalanceInquiryData();

        }
        /************************************ADD OTHER TRANSACTION OPERATIONS HERE *********************************/
        return "";
    }

    public String prepareTransactionsToBank(Transactions transactions, String username,SMS sms) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setAction("SMS");
        smsRequest.setUsername(username);
        smsRequest.setData(sms);
        return gson.toJson(smsRequest);
    }

    public String prepareTransactionsToBank(Transactions modifiedTransaction,Transactions originalTransaction,String username){
        logger.info("Operational ID::" + modifiedTransaction.getOperational_id());
        logger.info("Cash flow id" + transactionOperationRepository.selectCashFlowId(modifiedTransaction.getOperational_id()));
        Transaction_Operation transaction_operation =transactionOperationRepository.findTransaction_OperationById(modifiedTransaction.getOperational_id());
        Transaction_Operation original_transaction_operation = transactionOperationRepository.findTransaction_OperationById(originalTransaction.getOperational_id());
        String [] transaction_charges = {modifiedTransaction.getBank_income().toString(),modifiedTransaction.getAgent_commision().toString(),modifiedTransaction.getExcise_duty().toString()};

        switch (transaction_operation.getAction().toUpperCase()){
            case "ACCT_INQUIRY":
                AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
                ACRequestData acRequestData = new ACRequestData();
                switch (original_transaction_operation.getAction().toUpperCase()){
                    case "TRANSFER":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_to());
                    case "DEPOSIT":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_to());
                    case "WITHDRAW":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_from());
                    case "UTILITIES":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_to());
                    case "BAL":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_from());
                    case "MINI":
                        logger.info("AccountFrom"+modifiedTransaction.getAccount_from());
                        logger.info("AccountTo"+modifiedTransaction.getAccount_to());
                        acRequestData.setAccount(modifiedTransaction.getAccount_from());
                    case "FULL":
                        acRequestData.setAccount(modifiedTransaction.getAccount_from());


                }
                //acRequestData.setAccount(modifiedTransaction.getAccount_from());
                acRequestData.setRequestId(modifiedTransaction.getReceipt_number());
                acRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(modifiedTransaction.getAgent_id()).getDeviceId().toString());
                acRequestData.setTellerId(modifiedTransaction.getAgent_id().toString());
                acRequestData.setBranchId("000"+agentRepository.findBranchIdByAgentId(modifiedTransaction.getAgent_id()).toString());
                //BUILD FINAL REQUEST HERE
                accountInquiryRequest.setAction(transaction_operation.getAction());
                //accountInquiryRequest.setAction("ACCT_INQUIRY");
                accountInquiryRequest.setUsername(username);
                accountInquiryRequest.setData(acRequestData);
                logger.info(gson.toJson(accountInquiryRequest));
                return gson.toJson(accountInquiryRequest);
        }
        return "";
    }

}
