package compas.user;

import compas.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Repository
public interface  UserRepository extends CrudRepository<Users,Long> {

    Users findByAgentId(Integer agentId);
}
