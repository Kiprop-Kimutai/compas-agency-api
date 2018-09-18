package compas.agent;

import compas.models.Agent;
import compas.models.Agent1;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */


public interface AgentRepository extends CrudRepository<Agent,Long>{
     @Query("select a from Agent a where a.id = :Id and a.verified = 'v' ")
    public Agent findById(@Param("Id") Integer Id);

     @Query("select agent from  Agent agent where agent.id = :Id and agent.status = true")
     List<Agent> findActiveAgentById(@Param("Id") Integer Id);

    @Query("select agent.agent_description from  Agent agent where agent.id = :Id")
    public String  findActiveAgentDescritpionById(@Param("Id") Integer Id);

/*     @Query(value = "select agent.deposit_limit from Agent agent where agent.id =:Id")
     Double findAgentDepositLimitsByAgentId(@Param("Id") Integer Id);

     @Query(value = "select agent.withdrawal_limit from Agent agent where agent.id = :Id")
     Double findAgentWithdrawalLimitsByAgentId(@Param("Id")Integer Id);*/

     @Query(value = "select agent.daily_limit from Agent agent where agent.id = :Id")
     Double agentDailyTransactionLimit(@Param("Id")Integer Id);

     @Query(value = "select agent.weekly_limit from Agent agent where agent.id = :Id")
     Double agentWeeklyTransactionLimit(@Param("Id") Integer Id);

     @Query(value = "select agent.monthly_limit from Agent agent where agent.id = :Id")
     Double agentMonthlyTransactionLimit(@Param("Id")Integer Id);

     @Query(value = "select agent.quarterly_limit from Agent agent where agent.id =:Id")
     Double agentQuarterlyTransactionLimit(@Param("Id")Integer Id);

     @Query("select agent.branch_id from Agent agent where agent.id =:Id")
     Integer findBranchIdByAgentId(@Param("Id")Integer Id);


    @Query("select agent  from Agent agent where agent.id = :Id and agent.status =true")
    Agent findTransactingAgentById(@Param("Id")Integer Id);




}
