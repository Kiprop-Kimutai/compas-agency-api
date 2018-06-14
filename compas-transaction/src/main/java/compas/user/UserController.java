package compas.user;

import com.google.gson.Gson;
import compas.models.Users;
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
@RequestMapping(path="/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson = new Gson();
    @Autowired(required = false)
    private UserRepository userRepository;
    @RequestMapping(path = "/addUser",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    public ResponseEntity saveUser(@RequestBody String userString){
        Users users = userRepository.save(gson.fromJson(userString,Users.class));
        return ResponseEntity.status(201).body(gson.toJson(users));
    }

}
