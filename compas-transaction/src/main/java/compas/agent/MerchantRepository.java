package compas.agent;

import compas.models.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by CLLSDJACKT013 on 8/20/2018.
 */
public interface  MerchantRepository extends CrudRepository<Merchant,Long>{

    @Query("select merchant from Merchant merchant where merchant.id > 0 and merchant.id = :Id and merchant.verified = 'v' ")
    Merchant findMerchantByMerchantId(@Param("Id")Integer Id);
}
