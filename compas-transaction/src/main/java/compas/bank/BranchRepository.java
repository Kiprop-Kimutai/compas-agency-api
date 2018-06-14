package compas.bank;

import compas.models.Bank_Branch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Repository
public interface  BranchRepository extends CrudRepository<Bank_Branch,Long> {
    @Query("select b from Bank_Branch b where b.id = :Id")
     Bank_Branch findBankById(@Param("Id") Integer Id);
}
