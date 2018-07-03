package compas.accounts;

import compas.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
//This will be auto-implemented by Spring into a bean called customerRepository
@Repository
public interface AccountsRepository extends CrudRepository<Account,Long> {
    @Query("select acc from Account acc where acc.customer_id_number = :customer_id_number")
    List<Account> findByIdNumber(@Param("customer_id_number") String  customer_id_number);

    @Query("select acc from Account acc where acc.account_number=:account_number and acc.status=true")
    Account findActiveAccountByAccountNumber(@Param("account_number") String account_number);

    @Query("select acc.account_number from Account acc where acc.customer_id_number=:customer_id_number")
    String findAccountByIdNumber(@Param("customer_id_number")String customer_id_number);
}
