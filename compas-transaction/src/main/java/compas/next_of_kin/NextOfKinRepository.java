package compas.next_of_kin;

import compas.models.Next_of_Kin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
@Repository
public interface NextOfKinRepository extends CrudRepository<Next_of_Kin,Long> {
    //public Next_of_Kin findNext_of_KinById_numberAndName();
}
