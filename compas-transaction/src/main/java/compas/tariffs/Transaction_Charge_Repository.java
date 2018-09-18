package compas.tariffs;

import compas.models.Transaction_Charge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 7/17/2018.
 */
@Repository
public interface Transaction_Charge_Repository extends CrudRepository<Transaction_Charge,Long> {

    @Query("select transaction_charge from Transaction_Charge transaction_charge where transaction_charge.transaction_operation_id = :transaction_operation_id")
    List<Transaction_Charge> findTransaction_ChargeById(@Param("transaction_operation_id") Integer transaction_operation_id);

    @Query("select transaction_charge.charge from Transaction_Charge transaction_charge where transaction_charge.transaction_operation_id = :transaction_operation_id and transaction_charge.min_amount <= :amount and transaction_charge.max_amount >= :amount")
    Double findVariableTransaction_ChargeByTransactionOperationAndAmount(@Param("amount") Double amount,@Param("transaction_operation_id")Integer transaction_operation_id);

    @Query("select transaction_charge from Transaction_Charge transaction_charge where transaction_charge.min_amount <= :amount and transaction_charge.max_amount >= :amount and transaction_charge.transaction_operation_id = :transaction_operation_id")
    Transaction_Charge findTransaction_ChargeByLimitsAndTransaction_operation_id(@Param("amount") Double amount,@Param("transaction_operation_id")Integer transaction_operation_id);

    @Query("select charge_burden from Transaction_Charge transaction_charge where transaction_charge.transaction_operation_id =:transaction_operation_id")
    Integer findTransaction_ChargeBurdenByTransaction_operation_id(@Param("transaction_operation_id")Integer transaction_operation_id);
}
