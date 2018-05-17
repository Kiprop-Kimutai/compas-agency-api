package compas.tarrifs;

import compas.models.Tariff;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public interface TarrifsRepository extends CrudRepository<Tariff,Long> {
}
