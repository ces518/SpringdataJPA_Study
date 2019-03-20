package springdata.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 21:26
 **/
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
}
