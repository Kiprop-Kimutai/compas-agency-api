package compas.user;

import com.google.gson.Gson;
import compas.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson = new Gson();
    @Autowired
    private UserRepository userRepository;
    private ListExampleUsers listExampleUsers = new ListExampleUsers();
    @Autowired
    private ExampleUserRepository exampleUserRepository;
    @RequestMapping(path = "/addUser",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveUser(@RequestBody String userString){
        User user = userRepository.save(gson.fromJson(userString,User.class));
        return ResponseEntity.status(201).body(gson.toJson(user));
    }

                //MOCK OPERATIONS
    @RequestMapping(path="/registerUser", method=RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ExampleUser registerUsers(@RequestBody String abcuser){
        logger.info(abcuser);
        ExampleUser registerRequest = gson.fromJson(abcuser,ExampleUser.class);
        //check if user exists, if not proceed to register
        List<ExampleUser> existingUsers =  exampleUserRepository.findUsersByUsername(registerRequest.getUsername());
        if(existingUsers.size()>0){
            return new ExampleUser();
        }
        //validate password
        if(registerRequest.getPassword().length()<5){
            return null;
        }
        //register user
        exampleUserRepository.save(registerRequest);
        return registerRequest;

    }
    @RequestMapping(path = "/login",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public  Boolean login(@RequestBody String loginRequest){
        logger.info(loginRequest);
        ExampleUser sessionUser = gson.fromJson(loginRequest,ExampleUser.class);
        //check if user exists
        logger.info(sessionUser.toString());
         List<ExampleUser> users = exampleUserRepository.findUsersByUsername(sessionUser.getUsername());
        logger.info(""+users.size());
        for(Integer i = 0;i<users.size();i++){
            System.out.println(users.get(i).getUsername());
        }
        // users.forEach((x) ->logger.info("##count"));

     /*   //check if password matches
        String foundPassword = existingUsers.get(0).getPassword();
        if(sessionUser.getPassword().equalsIgnoreCase(foundPassword)){
            return true;
        }*/
        return false;
    }
}
