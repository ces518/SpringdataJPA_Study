package springdata.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springdata.jpa.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-13
 * Time: 21:51
 **/
/**
 * 다음과 같이
 * Repository를 직접 구현 할 수 도있다.
 */
@Repository
@Transactional
public class CommentRepository {

    /**
     * Spring의 종속성을 최대한 줄이기 위해
     * JPA의 annotation인
     * PersistenceContext를 사용하는것이 좋다.
     * Spring의 코드를 최대한 숨기는것이 스프링의 철학이기도하다.
     */
    //@Autowired
    @PersistenceContext
    EntityManager entityManager;

    public Comment create(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    public void delete(Comment comment) {
        entityManager.remove(comment);
    }

    public List<Comment> findAll(Comment comment) {
        return entityManager.createQuery("SELECT c FROM Comment c",Comment.class)
                .getResultList();
    }

}
