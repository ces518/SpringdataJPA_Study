package springdata.jpa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 21:35
 **/

/**
 * Custom Repository 를 위한 구현체
 */
@Repository
@Transactional
public class UserCustomRepositoryImpl implements UserCustomRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public List<User> findByUsername(String username) {

        return entityManager.createQuery("SELECT u FROM User u",User.class)
                            .getResultList();
    }
}
