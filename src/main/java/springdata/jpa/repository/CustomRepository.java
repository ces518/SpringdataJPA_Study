package springdata.jpa.repository;

import org.springframework.data.repository.RepositoryDefinition;
import springdata.jpa.domain.Comment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-17
 * Time: 20:48
 **/

/**
 * Repository 를 직접 정의 하기.
 */
//@RepositoryDefinition(domainClass = Comment.class,idClass = Long.class)
public interface CustomRepository extends MyRepository<Comment,Long>{

//    Comment save(Comment comment);

//    List<Comment> findAll();
}
