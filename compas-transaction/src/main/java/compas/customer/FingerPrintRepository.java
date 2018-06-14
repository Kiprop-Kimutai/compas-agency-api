package compas.customer;

import compas.models.Fingerprints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 20/05/2018.
 */
@Repository
public interface FingerPrintRepository extends CrudRepository<Fingerprints,Long> {

}
