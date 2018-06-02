package compas.transactionschannel;

import com.google.gson.Gson;
import compas.agent.AgentRepository;
import compas.device.Issued_DeviceRepository;
import compas.models.Transaction;
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
import compas.models.bankoperations.transfers.TransferRequest;
import compas.models.bankoperations.transfers.TransferRequestData;
import compas.models.bankoperations.withdrawal.WithdrawalRequest;
import compas.models.bankoperations.withdrawal.WithdrawalRequestData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.soap.SOAPBinding;

/**
 * Created by CLLSDJACKT013 on 31/05/2018.
 */
public class TransactionsToBank {
    private Gson gson = new Gson();
    private InquiriesRequestData inquiriesRequestData = new InquiriesRequestData();
    private InquiriesResponseData inquiriesResponseData = new InquiriesResponseData();
    private InquiriesRequest inquiriesRequest = new InquiriesRequest();
    private InquiriesResponse inquiriesResponse = new InquiriesResponse();
    public TransactionsToBank(){}
    @Autowired
    private TransactionOperationRepository transactionOperationRepository;
    @Autowired
    private Issued_DeviceRepository issued_deviceRepository;
    @Autowired
    private AgentRepository agentRepository;
    public String prepareTransactionsToBank(Transaction transaction,String Username){
        //guery action from transaction operation for posted transaction
        Transaction_Operation transaction_operation = transactionOperationRepository.findById(transaction.getOperational_id());
        if(transaction_operation.getAction().equalsIgnoreCase("BAL")){
            inquiriesRequestData.setTransId(transaction.getReceipt_number());
            inquiriesRequestData.setNarration(transaction_operation.getName());
            inquiriesRequestData.setAccount(transaction.getAccount_from());
            inquiriesRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            inquiriesRequestData.setTellerId(transaction.getAgent_id().toString());
            inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            inquiriesRequestData.setCustomerName("Jonah Hexx");
                    //BUILD FINAL Inquiries data
            inquiriesRequest.setUsername(Username);
            inquiriesRequest.setAction(transaction_operation.getAction());
            inquiriesRequest.setData(inquiriesRequestData);
            return gson.toJson(inquiriesRequest);
        }
        if(transaction_operation.getAction().equalsIgnoreCase("MINI")){
            //build other requests here
            inquiriesRequestData.setTransId(transaction.getReceipt_number());
            inquiriesRequestData.setNarration(transaction_operation.getName());
            inquiriesRequestData.setAccount(transaction.getAccount_from());
            inquiriesRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            inquiriesRequestData.setTellerId(transaction.getAgent_id().toString());
            inquiriesRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            inquiriesRequestData.setCustomerName("Martin Chirchir");
                            //BUILD FINAL REQUEST
            inquiriesRequest.setUsername(Username);
            inquiriesRequest.setAction(transaction_operation.getAction());
            inquiriesRequest.setData(inquiriesRequestData);
            return gson.toJson(inquiriesRequest);
        }
        if(transaction_operation.getAction().equalsIgnoreCase("TRANSFER")){
            TransferRequestData transferRequestData= new TransferRequestData();
            TransferRequest transferRequest = new TransferRequest();
            transferRequestData.setTransId(transaction.getReceipt_number());
            transferRequestData.setNarration(transaction_operation.getName());
            transferRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            transferRequestData.setTellerId(transaction.getAgent_id().toString());
            transferRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            transferRequestData.setCustomerName("Diana Machora");
            transferRequestData.setFromAccount(transaction.getAccount_from());
            transferRequestData.setToAccount(transaction.getAccount_to());
            transferRequestData.setAmount(transaction.getAmount().toString());
                            //BUILD FINAL Request
            transferRequest.setUsername(Username);
            transferRequest.setAction(transaction_operation.getAction());
            transferRequest.setData(transferRequestData);
            return gson.toJson(transferRequest);
        }

        if(transaction_operation.getAction().equalsIgnoreCase("REVERSAL")){
            ReversalRequestData reversalRequestData  = new ReversalRequestData();
            ReversalRequest reversalRequest = new ReversalRequest();
            reversalRequestData.setTransId(transaction.getReceipt_number());
            reversalRequestData.setNarration(transaction_operation.getName());
            reversalRequestData.setAccount(transaction.getAccount_from());
            reversalRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            reversalRequestData.setTellerId(transaction.getAgent_id().toString());
            reversalRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            reversalRequestData.setCustomerName("Alex Waiganjo");
            reversalRequestData.setOldTransId(transaction.getReceipt_number());
            reversalRequestData.setAmount(transaction.getAmount().toString());
                            //BUILD FINAL REQUEST HERE
            reversalRequest.setUsername(Username);
            reversalRequest.setAction(transaction_operation.getAction());
            reversalRequest.setData(reversalRequestData);

            return gson.toJson(reversalRequestData);
        }

        if(transaction_operation.getAction().equalsIgnoreCase("DEPOSIT")){
            DepositRequestData depositRequestData = new DepositRequestData();
            DepositRequest depositRequest = new DepositRequest();
            depositRequestData.setTransId(transaction.getReceipt_number());
            depositRequestData.setNarration(transaction_operation.getName());
            depositRequestData.setAccount(transaction.getAccount_from());
            depositRequestData.setAmount(transaction.getAmount().toString());
            depositRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            depositRequestData.setTellerId(transaction.getAgent_id().toString());
            depositRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            depositRequestData.setCustomerName("Mbilia Bel");
                            //BUILD FINAL REQUEST HERE
            depositRequest.setUsername(Username);
            depositRequest.setAction(transaction_operation.getAction());
            depositRequest.setData(depositRequestData);

            return gson.toJson(depositRequest);
        }

        if(transaction_operation.getAction().equalsIgnoreCase("WITHDRAW")){
            WithdrawalRequestData withdrawalRequestData = new WithdrawalRequestData();
            WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
            withdrawalRequestData.setTransId(transaction.getReceipt_number());
            withdrawalRequestData.setNarration(transaction_operation.getName());
            withdrawalRequestData.setAccount(transaction.getAccount_from());
            withdrawalRequestData.setAmount(transaction.getAmount().toString());
            withdrawalRequestData.setDeviceId(issued_deviceRepository.findOneIssued_DeviceByAgent_id(transaction.getAgent_id()).getDeviceId().toString());
            withdrawalRequestData.setTellerId(transaction.getAgent_id().toString());
            withdrawalRequestData.setBranchId(agentRepository.findBranchIdByAgentId(transaction.getAgent_id()).toString());
            withdrawalRequestData.setCustomerName("Zinedine Zidane");
                        //BUILD FINAL REQUEST HERE
            withdrawalRequest.setUsername(Username);
            withdrawalRequest.setAction(transaction_operation.getAction());
            withdrawalRequest.setData(withdrawalRequestData);
            return gson.toJson(withdrawalRequest);
        }

        if(transaction_operation.getAction().equalsIgnoreCase("ACCT_INQUIRY")){
            AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
            ACRequestData acRequestData = new ACRequestData();
            acRequestData.setAccount(transaction.getAccount_from());
                    //BUILD FINAL REQUEST HERE
            accountInquiryRequest.setAction(transaction_operation.getAction());
            accountInquiryRequest.setUsername(Username);
            accountInquiryRequest.setData(acRequestData);
            return gson.toJson(acRequestData);
        }
        /************************************ADD OTHER TRANSACTION OPERATIONS HERE *********************************/
        return "";
    }
}
