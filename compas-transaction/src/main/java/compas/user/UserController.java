package compas.user;

import com.google.gson.Gson;
import compas.models.Users;
import compas.models.apiresponse.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/compas/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson = new Gson();
    @Autowired(required = false)
    private UserRepository userRepository;

    ApiResponse apiResponse = new ApiResponse();
    @RequestMapping(path = "/addUser",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveUser(@RequestBody String userString){
        Users users = userRepository.save(gson.fromJson(userString,Users.class));
        return ResponseEntity.status(201).body(gson.toJson(users));
    }

    @ResponseBody
    @RequestMapping(path = "/login",method =RequestMethod.POST, produces = "application/json",consumes = "application/json")
    public ResponseEntity processLogin(@RequestBody String loginRequest){
        Users user = gson.fromJson(loginRequest,Users.class);
        List<Users> loggedInUser = userRepository.processUserLogin(user.getAgent_code(),user.getUsername(),user.getPassword());
        if(loggedInUser.size()==1){
            apiResponse.setResponse_code("150");
            apiResponse.setResponse_message("Login successful");
            logger.info(gson.toJson(apiResponse));
            return ResponseEntity.status(201).body(apiResponse);
        }
        else if(loggedInUser.size()>1){
            apiResponse.setResponse_code("151");
            apiResponse.setResponse_message("Ambiguous user creds");
            logger.info(gson.toJson(apiResponse));

            return ResponseEntity.status(201).body(apiResponse);
        }
        else{
            //no user found
            Users requestedUser = userRepository.findUserByUsernameAndAgent_code(user.getUsername(),user.getAgent_code()).get(0);
            if(requestedUser.getPassword_expired() || requestedUser.getLocked()){
                //username or password expired
                apiResponse.setResponse_code("153");
                apiResponse.setResponse_message("Login failed");
                logger.info(gson.toJson(apiResponse));
                return ResponseEntity.status(201).body(apiResponse);
            }
        }
        return ResponseEntity.status(201).body("");
    }

}
