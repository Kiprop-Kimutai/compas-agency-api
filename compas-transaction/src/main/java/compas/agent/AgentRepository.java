package compas.agent;

import compas.models.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Id;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
public interface AgentRepository extends CrudRepository<Agent,Long>{
     @Query("select a from Agent a where a.id = :Id")
    public Agent  findById( @Param("Id") Integer Id);


}
