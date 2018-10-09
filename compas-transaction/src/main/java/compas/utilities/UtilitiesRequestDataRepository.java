package compas.utilities;

import compas.models.utilities.UtilityRequest;
import compas.models.utilities.UtilityRequestData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
@Repository
public interface UtilitiesRequestDataRepository  extends CrudRepository<UtilityRequestData,Long>{
//    @Query("select utility from Utilities utility where utility.id =:Id ")
//    List<UtilityRequest> findUtilityRequestDataById(@Param("Id") Integer Id);
}
