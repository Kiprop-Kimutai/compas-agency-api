package compas.utilities;

import compas.models.Utilities;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 06/06/2018.
 */
@Repository
public interface UtilitiesRepository extends CrudRepository<Utilities,Long> {

    @Query("select utility from Utilities utility where utility.id =:Id ")
    List<Utilities> findUtilitiesById(@Param("Id") Integer Id);

    @Query("select '*' from Utilities ")
    List<Utilities>fetchAllUtilities();

    @Query("select utility from Utilities utility where utility.utility_code =:utility_code")
    Utilities findUtilityByUtility_code(@Param("utility_code") String utility_code);

    List<Utilities> findAll();
}
