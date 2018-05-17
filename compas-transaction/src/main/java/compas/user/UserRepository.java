package compas.user;

import compas.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public interface  UserRepository extends CrudRepository<User,Long> {

    User findByAgentId(Integer agentId);
}
