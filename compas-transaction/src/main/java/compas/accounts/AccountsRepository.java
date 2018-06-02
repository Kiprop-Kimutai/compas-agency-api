package compas.accounts;

import compas.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
//This will be auto-implemented by Spring into a bean called customerRepository
public interface AccountsRepository extends CrudRepository<Account,Long> {
    @Query("select acc from Account acc where acc.customer_id_number = :customer_id_number")
    List<Account> findByIdNumber(@Param("customer_id_number") String  customer_id_number);
}
