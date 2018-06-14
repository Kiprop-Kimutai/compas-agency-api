package compas.repositories;

import compas.agent.AgentRepository;
import compas.models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class AgentOperations {
    @Autowired
    private AgentRepository agentRepository;

    public Agent findById(Integer Id){
        return  agentRepository.findById(Id);
    }

    public List<Agent> findActiveAgentById(Integer Id){
        return agentRepository.findActiveAgentById(Id);
    }

    public String findActiveAgentDescriptionById(Integer Id){
        return agentRepository.findActiveAgentDescritpionById(Id);
    }

    public Double  findAgentDepositLimitsByAgentId(Integer Id){
        return agentRepository.findAgentDepositLimitsByAgentId(Id);
    }

    public Double findAgentWithdrawalLimitsByAgentId(Integer Id){
        return agentRepository.findAgentWithdrawalLimitsByAgentId(Id);
    }

    public Integer findBranchIdByAgentId(Integer Id){
        return agentRepository.findBranchIdByAgentId(Id);
    }

    public  Agent findTransactingAgentById(Integer Id){
        return agentRepository.findTransactingAgentById(Id);
    }



}
