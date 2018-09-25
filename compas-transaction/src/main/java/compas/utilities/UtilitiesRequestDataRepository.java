package compas.utilities;

import compas.models.utilities.UtilityRequestData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
@Repository
public interface UtilitiesRequestDataRepository  extends CrudRepository<UtilityRequestData,Long>{

}
