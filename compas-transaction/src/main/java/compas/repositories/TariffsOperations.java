package compas.repositories;

import compas.models.Tariff;
import compas.tariffs.TariffsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class TariffsOperations {
    @Autowired
    private TariffsRepository tariffsRepository;




}
