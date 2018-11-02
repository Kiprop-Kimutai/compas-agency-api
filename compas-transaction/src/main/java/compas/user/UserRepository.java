package compas.user;

import compas.models.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@Repository
public interface  UserRepository extends CrudRepository<Users,Long> {

    @Query("select user from Users user where user.agent_code =:agent_code")
    List<Users> findUsersByAgentCode(@Param("agent_code")String agent_code);

    @Query("select user from Users user where user.agent_code = :agent_code and user.username = :username and user.password =:password")
    List<Users> processUserLogin(@Param("agent_code")String agent_code,@Param("username")String username,@Param("password")String password);

<<<<<<< HEAD
    @Query("update Users user set user.password = :password,user. where user.agent_code =:agent_code and user.username =:username")
=======
    @Query("select user from Users user where user.username = :username and user.agent_code = :agent_code")
    List<Users> CheckUserExists(@Param("username")String username,@Param("agent_code")String agent_code);
    //Update Password
    @Transactional
    @Modifying
    @Query("update Users user set user.password = :password,firstlogin='0' where user.agent_code = :agent_code and user.username =:username")
>>>>>>> e935b1541eed472e988ae9bec1577ed567636090
    void updateUserPassword(@Param("agent_code")String agent_code,@Param("username")String username,@Param("password")String password);
    //Lock user
    @Transactional
    @Modifying
    @Query("update Users user set user.locked= '1' where user.agent_code = :agent_code and user.username =:username")
    void LockUser(@Param("agent_code") String agent_code,@Param("username") String username);
}
