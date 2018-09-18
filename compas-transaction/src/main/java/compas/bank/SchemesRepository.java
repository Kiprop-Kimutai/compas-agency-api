package compas.bank;

import compas.models.Schemes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
public interface  SchemesRepository extends CrudRepository<Schemes,Long> {
    @Query("select scheme from Schemes scheme where scheme.id=:bankId")
    List<Schemes> findSchemesByBankId(@Param("bankId") Integer bankId);

}
