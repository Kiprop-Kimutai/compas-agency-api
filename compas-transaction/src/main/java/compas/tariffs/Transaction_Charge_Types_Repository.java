package compas.tariffs;

import compas.models.Transaction_Charge_Types;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 7/17/2018.
 */
@Repository
public interface Transaction_Charge_Types_Repository extends CrudRepository<Transaction_Charge_Types,Long> {
    @Query("select transaction_charge_type from Transaction_Charge_Types  transaction_charge_type where transaction_charge_type.id = :Id")
    Transaction_Charge_Types findTransaction_Charge_TypesById(@Param("Id") Integer Id);

}
