package compas.currency;

import compas.models.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 18/05/2018.
 */
@Repository
public interface CurrencyRepository extends CrudRepository<Currency,Long>{
    public List<Currency> findAll();
}
