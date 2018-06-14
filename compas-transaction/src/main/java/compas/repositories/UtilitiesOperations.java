package compas.repositories;

import compas.models.Utilities;
import compas.utilities.UtilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class UtilitiesOperations {
    @Autowired
    private UtilitiesRepository utilitiesRepository;

    public List<Utilities> findUtilitiesById(Integer Id){
        return  utilitiesRepository.findUtilitiesById(Id);
    }

    public List<Utilities>fetchAllUtilities(){
        return utilitiesRepository.fetchAllUtilities();
    }

    public  List<Utilities> findAll(){
        return  utilitiesRepository.findAll();
    }

}
