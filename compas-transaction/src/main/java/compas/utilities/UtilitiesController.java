package compas.utilities;

import compas.models.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 06/06/2018.
 */
@Controller
public class UtilitiesController {
    @Autowired
    private UtilitiesRepository utilitiesRepository;

    public List<Utilities> findUtilitiesById(Integer Id){
        return utilitiesRepository.findUtilitiesById(Id);
    }
    public List<Utilities> fetchAllUtilities(){
        return utilitiesRepository.fetchAllUtilities();
    }
    public  List<Utilities> fetchAll(){
        return  utilitiesRepository.findAll();
    }
}
