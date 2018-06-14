package compas.authentications;

import compas.models.Auth_Mode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 18/05/2018.
 */
@Repository
public interface  AuthenticationModeRepository extends CrudRepository<Auth_Mode,Long> {
    public List<Auth_Mode> findAll();

    @Query("select auth_mode from Auth_Mode  auth_mode where auth_mode.id=:id")
    Auth_Mode findAuth_ModeById(@Param("id") Integer id);
}
