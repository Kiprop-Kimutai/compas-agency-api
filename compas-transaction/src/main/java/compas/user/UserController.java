package compas.user;

import com.google.gson.Gson;
import compas.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson = new Gson();
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(path = "/addUser",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveUser(@RequestBody String userString){
        User user = userRepository.save(gson.fromJson(userString,User.class));
        return ResponseEntity.status(201).body(gson.toJson(user));
    }
}
