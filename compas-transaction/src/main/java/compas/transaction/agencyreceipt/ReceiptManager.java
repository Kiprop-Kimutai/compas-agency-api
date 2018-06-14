package compas.transaction.agencyreceipt;

import compas.agent.AgentRepository;
import compas.models.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@RestController
@RequestMapping(path = "/cool")
public class ReceiptManager {

    @Autowired(required = false)
    private AgentRepository agentRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private Logger logger = LoggerFactory.getLogger(ReceiptManager.class);

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String generateReceiptNumber(Integer agentId){
        //look up at agent's table for agent code with supplied Id
        logger.info("<<>>"+agentId);
        Agent agent = agentRepository.findById(agentId);
        logger.info(agent.getString());
        Date date = new Date();
        String dateStamp = sdf.format(date);
        Integer lastThreeDigits = generateThreeDigitRandomNumbers(1000,100).intValue();
        String receiptNumber = agent.getAgent_code().substring(agent.getAgent_code().length()-3,agent.getAgent_code().length()).concat(dateStamp.substring(dateStamp.length()-3)).concat(lastThreeDigits.toString());
        logger.info(receiptNumber);
        return receiptNumber;
    }

    public Double generateThreeDigitRandomNumbers(Integer max,Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }
}
