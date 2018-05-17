package compas.bank;

import compas.models.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public interface BankRepository extends CrudRepository<Bank,Long> {
    @Query("select bank from Bank bank where bank.id = :Id")
    public Bank findBankById(@Param("Id") Integer Id);
}
