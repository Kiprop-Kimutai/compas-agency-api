package compas.repositories;

import compas.models.Users;
import compas.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class UserOperations {
    @Autowired
    private UserRepository userRepository;

  /*  public Users findByAgentId(String agentcode){
        //return userRepository.findUsersByAgentCode(agentcode);

    }*/

}
