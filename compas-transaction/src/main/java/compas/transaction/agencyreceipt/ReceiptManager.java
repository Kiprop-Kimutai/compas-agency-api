package compas.transaction.agencyreceipt;

import compas.agent.AgentRepository;
import compas.models.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class ReceiptManager {
    @Autowired
    private AgentRepository agentRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private Logger logger = LoggerFactory.getLogger(ReceiptManager.class);

    public String generateReceiptNumber(Integer agentId){
        //look up at agent's table for agent code with supplied Id
        Agent agent = agentRepository.findById(agentId);

        Date date = new Date();
        String dateStamp = sdf.format(date);
        String receiptNumber = agent.getAgent_code().substring(agent.getAgent_code().length()-3,agent.getAgent_code().length()).concat("-").concat(dateStamp.substring(dateStamp.length()-3)).concat(":").concat(generateThreeDigitRandomNumbers(1000,100).toString());
        logger.info(receiptNumber);
        return receiptNumber;
    }

    public Double generateThreeDigitRandomNumbers(Integer max,Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }
}
