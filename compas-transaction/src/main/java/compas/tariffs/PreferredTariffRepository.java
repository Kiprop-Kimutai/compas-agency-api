package compas.tariffs;

import compas.models.Preferential_Tariff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 8/3/2018.
 */
@Repository
public interface PreferredTariffRepository extends CrudRepository<Preferential_Tariff,Long>{
    @Query("select prefered_tariff from Preferential_Tariff prefered_tariff where prefered_tariff.agent_id = :agent_id  and prefered_tariff.transaction_operation_id =:transaction_operation_id")
    Preferential_Tariff findPreferredTariffByAgentIdAndTransactionOperation(@Param("agent_id")Integer agent_id, @Param("transaction_operation_id")Integer transaction_operation_id);
}
