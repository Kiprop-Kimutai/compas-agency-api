package compas.authentications;

import compas.models.ApiSecurity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/25/2018.
 */
//@Repository
public interface  ApiKeyRepository extends CrudRepository<ApiSecurity,Long> {
    @Query("select apisecurity from ApiSecurity  apisecurity where apisecurity.username =:username and apisecurity.action = :action")
    List<ApiSecurity> findByUsername(@Param("username")String username,@Param("action") String action);
}
