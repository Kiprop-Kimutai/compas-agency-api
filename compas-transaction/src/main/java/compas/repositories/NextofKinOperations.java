package compas.repositories;

import compas.next_of_kin.NextOfKinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class NextofKinOperations {
    @Autowired
    private NextOfKinRepository nextofKinOperations;
}
