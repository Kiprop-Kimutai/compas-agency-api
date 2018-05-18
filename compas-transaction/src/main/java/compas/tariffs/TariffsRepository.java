package compas.tariffs;

import compas.models.Tariff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public interface TariffsRepository extends CrudRepository<Tariff,Long> {
    @Query("select tariff from Tariff  tariff where tariff.transaction_type_id = :transaction_type_id and tariff.min_amount <= :amount and tariff.max_amount >= :amount")
     Tariff findTransactionTariffByTransaction_type_idAndAmount(@Param("transaction_type_id") Integer transaction_type_id,@Param("amount") Double amount);
}
