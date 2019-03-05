package springdata.jpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springdata.jpa.domain.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-05
 * Time: 21:21
 **/
@Component
public class JpaRunner implements ApplicationRunner {


    /**
     * JPA의 핵심클래스인 entityManager를 주입받아 사용할수있다.
     *
     */
    @PersistenceContext
    EntityManager entityManager;

    /**
     * JPA의 모든 영속화 활동은 한 트랜잭션내에서 이루어져야한다.
     * Spring이 제공하는 transactionManager의 기능을 사용 할 수 있다.
     * @param args
     * @throws Exception
     */
    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("june");
        account.setPassword("jpa");

        // EntityManager를 이용하여 도메인객체를 데이터 베이스에 영속화 시킬수있다.
        entityManager.persist(account);

        // JPA는 하이버네이트를 사용하기 떄문에 하이버네이트 API를 직접 사용할수있다.

        Session session = entityManager.unwrap(Session.class);

        Account account1 = new Account();
        account1.setUsername("my-june");
        account1.setPassword("hibernate");
        session.save(account1);
    }
}
