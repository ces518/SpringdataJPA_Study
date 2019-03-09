package springdata.jpa;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springdata.jpa.domain.Account;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-09
 * Time: 00:06
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    EntityManager entityManager;


    @Test
    public void test() {
        //given
        Session session = entityManager.unwrap(Session.class);
        Account account = session.find(Account.class, 7);
        Set<String> phoneNumbers = account.getPhoneNumbers();
        log.debug("phoneNumbers= {}",phoneNumbers.size());
        //when
        phoneNumbers.add("011-111-1111");
        session.flush();

        //then

    }
}
