package compas.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 30/05/2018.
 */
public interface ExampleUserRepository  extends CrudRepository<ExampleUser,Long>{

    @Query("select '*' from ExampleUser  exampleuser where exampleuser.username = :username ")
    List<ExampleUser> findUsersByUsername(@Param("username")String username);

/*    @Query("select '*' from ExampleUser  exampleuser where exampleuser.username = :username ")
    ListExampleUsers findAllUsersByUsername(@Param("username")String username);*/
}
