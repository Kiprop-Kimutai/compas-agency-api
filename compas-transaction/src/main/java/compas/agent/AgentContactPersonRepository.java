package compas.agent;

import compas.models.AgentContactPersons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
public interface AgentContactPersonRepository extends CrudRepository<AgentContactPersons,Long>{
    @Query("select contactpersons from AgentContactPersons contactpersons where contactpersons.agent_code=:agent_code")
    List<AgentContactPersons> findContactPersonsByAgentCode(@Param("agent_code")String agent_code);
}
