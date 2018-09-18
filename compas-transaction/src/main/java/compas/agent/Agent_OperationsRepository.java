package compas.agent;

import compas.models.Agent_Operations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
public interface  Agent_OperationsRepository  extends CrudRepository<Agent_Operations,Long> {
    @Query("select agent_operations from Agent_Operations agent_operations where agent_operations.agent_id =:agent_id and agent_operations.transaction_operation_id >0")
    List<Agent_Operations> findAgent_OperationsByAgent_id(@Param("agent_id") Integer agent_id);
}
