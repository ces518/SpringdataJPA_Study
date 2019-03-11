package springdata.jpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springdata.jpa.domain.Account;
import springdata.jpa.domain.Comment;
import springdata.jpa.domain.Post;
import springdata.jpa.domain.Study;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

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

        /*
          Transient 상태
        * 객체를 new로 생성하고 setter를 통해 데이터를 세팅한상태.
        *
        * */
        Account account = new Account();
        account.setUsername("june");
        account.setPassword("jpa");

        account.addNumber("010-1234-1234");
        account.addNumber("010-1234-1235");
        account.addNumber("010-1234-1236");

        Study study = new Study();
        study.setName("spring study");
        /*
        * 양방향 관계에서 owner에 관계를 설정해 주지 않으면
        * DB에 반영이 되지않는다.
        *
        * 객체지향적으로 봤을때 양쪽 엔티티 모두 관계를 설정해주어야한다.
        * 아래와 같이 양방향 관계에서 관계를 설정해주는 메서드를 묶어주는것이 옳다
        * 해당 메서드를 컨비니언트메서드라고한다.
        * */
        study.setOwner(account);
        account.getStudies().add(study);

        // EntityManager를 이용하여 도메인객체를 데이터 베이스에 영속화 시킬수있다.
        entityManager.persist(account);

        // JPA는 하이버네이트를 사용하기 떄문에 하이버네이트 API를 직접 사용할수있다.

        Session session = entityManager.unwrap(Session.class);

        Account account1 = new Account();
        account1.setUsername("my-june");
        account1.setPassword("hibernate");

        /*
        * Persistent 상태
        * save를 했다고해서  DB에 바로저장되는것은 아니다.
        * JPA의 관리대상이 되었을뿐이다.
        * */
        session.save(account1);
        session.save(study);

        /*
        1차캐시란 ?
        save를 호출했다고 해서 INSERT QUERY가 바로 발생하는것이 아니라.
        Persistent Context객체에 영속화 되었다가
        Transaction이 종료되는 시점에 Insert Query가 발생한다.
        즉, save를 한뒤 다시 로드하더라도, select쿼리는 발생하지않는다.
         */
        Account june = session.load(Account.class, account1.getSeq());

        /*
         * Persistent상태가 되면, JPA는 해당 객체의 상태를 게속 감시한다.(Dirty Checking)
           해당 객체에 변경사항이 일어날경우 자동적으로 반영을해준다. (Write Behind)
           update Query를 날리지않았는데도 update쿼리가 자동적으로 발생함.
         */
        june.setUsername("park-june0");


        Post post = new Post();
        post.setTitle("게시글");

        Comment comment = new Comment();
        comment.setComment("댓글 1");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("댓글 2");
        post.addComment(comment1);

        session.save(post);
    }
}
