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
import org.springframework.transaction.annotation.Transactional;
import springdata.jpa.domain.Account;
import springdata.jpa.domain.Comment;
import springdata.jpa.domain.Post;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Transactional
    @Test
    public void before() {
        Post post = new Post();
        post.setTitle("게시글 입니다.");

        Comment comment = new Comment();
        comment.setComment("댓글1");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("댓글2");
        post.addComment(comment1);

        Session session = entityManager.unwrap(Session.class);
        session.save(post);
    }

    @Test
    public void lazy() {
        //given
        Session session = entityManager.unwrap(Session.class);

        //when
        Post post = session.get(Post.class, 13L);

        //then
        log.debug("post = {}",post);
        assertThat(post).isNotNull();
    }
}
