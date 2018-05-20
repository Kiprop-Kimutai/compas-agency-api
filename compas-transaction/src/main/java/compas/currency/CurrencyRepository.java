package compas.currency;

import compas.models.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 18/05/2018.
 */
public interface CurrencyRepository extends CrudRepository<Currency,Long>{
    public List<Currency> findAll();
}
