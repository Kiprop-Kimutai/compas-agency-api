package compas.tariffs;

import compas.models.Tariff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@Repository
public interface TariffsRepository extends CrudRepository<Tariff,Long> {
/*    @Query("select tariff from Tariff  tariff where tariff.transaction_type_id = :transaction_type_id and tariff.min_amount <= :amount and tariff.max_amount >= :amount")
     Tariff findTransactionTariffByTransaction_type_idAndAmount(@Param("transaction_type_id") Integer transaction_type_id,@Param("amount") Double amount);

    @Query("select tariff from Tariff  tariff where tariff.operation_id = :operation_id and tariff.min_amount <= :amount and tariff.max_amount >= :amount")
    Tariff findTransactionTariffByOperation_idAndAmount(@Param("operation_id") Integer operation_id,@Param("amount") Double amount);*/

    @Query("select tariff from Tariff tariff where tariff.transaction_operation_id = :transaction_operation_id")
    Tariff findTariffbyTransactionOperationId(@Param("transaction_operation_id") Integer transaction_operation_id);
}
