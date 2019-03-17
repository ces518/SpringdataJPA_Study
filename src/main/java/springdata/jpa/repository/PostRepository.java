package springdata.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 제목이 포함되는 것을 검색
    Page<Post> findByTitleContains(String title, Pageable pageable);

    // counting 쿼리도 만들수있다.
    long countByTitleContains(String title);
}
