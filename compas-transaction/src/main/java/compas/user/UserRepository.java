package compas.user;

import compas.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Repository
public interface  UserRepository extends CrudRepository<Users,Long> {

    //Users findByAgentId(Integer agentId);
    //List<Users> findByAgent_code(String agentCode);
    @Query("select user from Users user where user.agent_code =:agent_code")
    List<Users> findUsersByAgentCode(@Param("agent_code")String agent_code);
}
