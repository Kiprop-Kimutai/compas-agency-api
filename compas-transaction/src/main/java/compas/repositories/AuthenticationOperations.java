package compas.repositories;

import compas.authentications.AuthenticationModeRepository;
import compas.models.Auth_Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class AuthenticationOperations {
    @Autowired
    private AuthenticationModeRepository authenticationModeRepository;

    public List<Auth_Mode> findAll(){
        return authenticationModeRepository.findAll();
    }

    public Auth_Mode findAuth_ModeById(@Param("id") Integer id){
        return authenticationModeRepository.findAuth_ModeById(id);
    }


}
