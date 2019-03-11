package springdata.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springdata.jpa.domain.Post;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-11
 * Time: 21:58
 **/
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p")
    List<Post> findAll();
}
