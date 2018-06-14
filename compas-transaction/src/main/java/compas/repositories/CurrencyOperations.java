package compas.repositories;

import compas.currency.CurrencyRepository;
import compas.models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class CurrencyOperations {
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> findAll(){
        return  currencyRepository.findAll();
    }

}
