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
import compas.models.bankoperations.Inquiries.InquiriesRequest;
import compas.models.bankoperations.Inquiries.InquiriesRequestData;
import compas.models.bankoperations.Inquiries.InquiriesResponse;
import compas.models.bankoperations.Inquiries.InquiriesResponseData;
import compas.models.bankoperations.deposit.DepositRequest;
import compas.models.bankoperations.deposit.DepositRequestData;
import compas.models.bankoperations.reversals.ReversalRequest;
import compas.models.bankoperations.reversals.ReversalRequestData;
import compas.models.bankoperations.sms.SmsRequest;
import compas.models.bankoperations.transfers.TransferRequest;
import compas.models.bankoperations.transfers.TransferRequestData;
import compas.models.bankoperations.withdrawal.WithdrawalRequest;
import compas.models.bankoperations.withdrawal.WithdrawalRequestData;
import compas.txn_params.TransactionOperationsRepository;
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
    @Autowired
    private TransactionOperationsRepository transactionOperationRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AccountsRepository accountsRepository;

    public String prepareTransactionsToBank(Transactions transactions, String Username) {
        //guery action from transactions operation for posted transactions
        logger.info("Operational ID::" + transactions.getOperational_id());
        logger.info("Cash flow id" + transactionOperationRepository.selectCashFlowId(transactions.getOperational_id()));
        transactionOperationRepository.findTransaction_OperationById(transactions.getOperational_id());
        Transaction_Operation transaction_operation = transactionOperationRepository.findTransaction_OperationById(transactions.getOperational_id());
        //try switch here

        switch (transaction_operation.getAction().toUpperCase()) {
            case "BAL":
                inquiriesRequestData.setTransId(transactions.getReceipt_number());
                inquiriesRequestData.setNarration(transaction_operation.getName());
                inquiriesRequestData.setAccount(transactions.getAccount_from());
                inquiriesRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                inquiriesRequestData.setTellerId(transactions.getAgent_id().toString());
                inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()));
                inquiriesRequestData.setCustomerName("Jonah Hexx");
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
                inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()));
                inquiriesRequestData.setCustomerName("Martin Chirchir");
                //BUILD FINAL REQUEST
                inquiriesRequest.setUsername(Username);
                inquiriesRequest.setAction(transaction_operation.getAction());
                inquiriesRequest.setData(inquiriesRequestData);
                return gson.toJson(inquiriesRequest);

            case "TRANSFER":
                transferRequestData.setTransId(transactions.getReceipt_number());
                transferRequestData.setNarration(transaction_operation.getName());
                transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                transferRequestData.setTellerId(transactions.getAgent_id().toString());
                transferRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                transferRequestData.setCustomerName("Diana Machora");
                transferRequestData.setFromAccount(transactions.getAccount_from());
                transferRequestData.setToAccount(transactions.getAccount_to());
                transferRequestData.setAmount(transactions.getAmount().toString());
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setData(transferRequestData);
                return gson.toJson(transferRequest);

            case "REVERSAL":
                ReversalRequestData reversalRequestData = new ReversalRequestData();
                ReversalRequest reversalRequest = new ReversalRequest();
                reversalRequestData.setTransId(transactions.getReceipt_number());
                reversalRequestData.setNarration(transaction_operation.getName());
                reversalRequestData.setAccount(transactions.getAccount_from());
                reversalRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transactions.getAgent_id()).getDeviceId().toString());
                reversalRequestData.setTellerId(transactions.getAgent_id().toString());
                reversalRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                reversalRequestData.setCustomerName("Alex Waiganjo");
                reversalRequestData.setOldTransId(transactions.getReceipt_number());
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
                transferRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                transferRequestData.setCustomerName("Diana Machora");
                transferRequestData.setToAccount(transactions.getAccount_from());
                transferRequestData.setFromAccount(accountsRepository.findAccountByIdNumber(agentRepository.findById(transactions.getAgent_id()).getAgent_id_number()));
                transferRequestData.setAmount(transactions.getAmount().toString());
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
                transferRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transactions.getAgent_id()).toString());
                transferRequestData.setCustomerName("Jonah Hexx");
                transferRequestData.setFromAccount(transactions.getAccount_from());
                transferRequestData.setToAccount(accountsRepository.findAccountByIdNumber(agentRepository.findById(transactions.getAgent_id()).getAgent_id_number()));
                transferRequestData.setAmount(transactions.getAmount().toString());
                //BUILD FINAL Request
                transferRequest.setUsername(Username);
                //transferRequest.setAction(transaction_operation.getAction());
                transferRequest.setAction("TRANSFER");
                transferRequest.setData(transferRequestData);
                return gson.toJson(transferRequest);

            case "ACCT_INQUIRY":
                AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
                ACRequestData acRequestData = new ACRequestData();
                acRequestData.setAccount(transactions.getAccount_from());
                //BUILD FINAL REQUEST HERE
                accountInquiryRequest.setAction(transaction_operation.getAction());
                accountInquiryRequest.setUsername(Username);
                accountInquiryRequest.setData(acRequestData);
                return gson.toJson(acRequestData);

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

}
