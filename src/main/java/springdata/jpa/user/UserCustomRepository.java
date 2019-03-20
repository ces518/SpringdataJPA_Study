package springdata.jpa.user;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 21:35
 **/

/**
 * Custom Repository를 위한 인터페이스
 */
public interface UserCustomRepository {

    List<User> findByUsername(String username);

}
