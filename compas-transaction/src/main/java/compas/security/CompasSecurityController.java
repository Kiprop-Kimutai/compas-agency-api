package compas.security;

import com.google.gson.Gson;
import compas.authentications.ApiKeyRepository;
import compas.models.ApiSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/26/2018.
 */

@Repository
@RestController
@RequestMapping(path="/securitycontroller")
public class CompasSecurityController {
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private CompasSignatureGenerator compasSignatureGenerator;
    //CompasSignatureGenerator compasSignatureGenerator = new CompasSignatureGenerator();
    private Logger logger = LoggerFactory.getLogger(CompasSecurityController.class);
    @ResponseBody
    @RequestMapping(path = "/generatepwd",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity generateKey(){
        logger.info("Init....");
        ApiSecurity apiSecurity = compasSignatureGenerator.getHashedPassword("jonahkipropkimutaikipkeuelishakopchepkemoi","Kiprop34@","Kiprop34@".toCharArray(),"test",compasSignatureGenerator.generateSalt("jonahkipropkimutaikipkeuelishakopchepkemoi"));
        apiKeyRepository.save(apiSecurity);

        return ResponseEntity.status(201).body("ok");
    }

    @ResponseBody
    @RequestMapping(path = "/generatehashkey",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity generateHashedKey(@RequestBody String requestString){
        logger.info("Init....");
        ApiSecurity apiSecurityRequest = new Gson().fromJson(requestString,ApiSecurity.class);
        ApiSecurity apiSecurity = compasSignatureGenerator.getHashedPassword(apiSecurityRequest.getApi_key(),apiSecurityRequest.getUsername(),apiSecurityRequest.getPassword().toCharArray(),apiSecurityRequest.getAction(),compasSignatureGenerator.generateSalt(apiSecurityRequest.getApi_key()));
        //ApiSecurity apiSecurity = compasSignatureGenerator.getHashedPassword("jonahkipropkimutaikipkeuelishakopchepkemoi","Kiprop34@","Kiprop34@".toCharArray(),"test",compasSignatureGenerator.generateSalt("jonahkipropkimutaikipkeuelishakopchepkemoi"));
        apiKeyRepository.save(apiSecurity);

        return ResponseEntity.status(201).body("ok...");
    }
    @ResponseBody
    @RequestMapping(path = "/compare",method = RequestMethod.POST,consumes = "application/json",produces = "application/josn")
    public ResponseEntity comparePassword(HttpServletRequest request,@RequestBody String requestLogin){
        logger.info("Init...");
        String hash_string = request.getHeader("hash_string");
        String api_key = request.getHeader("api_key");
        String username = request.getHeader("username");
        String action = request.getHeader("action");

        logger.info("--->"+hash_string);
       // ApiSecurity apiSecurity = new Gson().fromJson(requestLogin,ApiSecurity.class);
        ApiSecurity apiSecurity = new ApiSecurity(api_key,username,DatatypeConverter.parseBase64Binary(hash_string),action);
        //apiSecurity.setHash(DatatypeConverter.parseBase64Binary(apiSecurity.getHash_string()));
        //apiSecurity.setHash(DatatypeConverter.parseBase64Binary(hash_string));
        logger.info(new Gson().toJson(apiSecurity));
        Boolean didMatch = compasSignatureGenerator.didhashmatch(apiSecurity.getUsername(),apiSecurity.getAction(),apiSecurity.getHash());
        logger.info("------------password status----------");
        logger.info("<{}>"+didMatch);
        return ResponseEntity.status(201).body("ok...");
    }

    public Boolean isValidApiKey(HttpServletRequest request/*String hash_string,String api_key,String username,String action*/){
        logger.info("Init...");
        String hash_string = request.getHeader("hash_string")!=null?request.getHeader("hash_string"):"";
        String api_key = request.getHeader("api_key")!=null ?request.getHeader("api_key"):"";
        String username = request.getHeader("username") !=null ?request.getHeader("username"):"";
        String action = request.getHeader("action") !=null ? request.getHeader("action"):"";

        logger.info("--->"+hash_string);
        // ApiSecurity apiSecurity = new Gson().fromJson(requestLogin,ApiSecurity.class);
        ApiSecurity apiSecurity = new ApiSecurity(api_key,username,DatatypeConverter.parseBase64Binary(hash_string),action);
        logger.info(new Gson().toJson(apiSecurity));
        Boolean didMatch = compasSignatureGenerator.didhashmatch(apiSecurity.getUsername(),apiSecurity.getAction(),apiSecurity.getHash());
        logger.info("------------password status----------");
        logger.info("<{}>"+didMatch);
        return didMatch;
    }

    @ResponseBody
    @RequestMapping(path = "/gethash",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity getBase64Hash(@RequestBody String apiSecurityString){
        logger.info("Init....");
        ApiSecurity apiSecurity = new Gson().fromJson(apiSecurityString,ApiSecurity.class);
        //ApiSecurity apiSecurity = compasSignatureGenerator.getHashedPassword("jonahkipropkimutaikipkeuelishakopchepkemoi","Kiprop34@","Kiprop34@".toCharArray(),"test",compasSignatureGenerator.generateSalt("jonahkipropkimutaikipkeuelishakopchepkemoi"));
        List<ApiSecurity> apiKeys = apiKeyRepository.findByUsername(apiSecurity.getUsername(),apiSecurity.getAction());
        logger.info("---------------------");
        logger.info(new Gson().toJson(apiKeys.get(0)));
        String base64String = DatatypeConverter.printBase64Binary(apiKeys.get(0).getHash());
        logger.info("++"+base64String);
        byte[] byteArray = DatatypeConverter.parseBase64Binary(base64String);
        logger.info("++"+byteArray);
        return ResponseEntity.status(201).body(base64String);
    }
}
