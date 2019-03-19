package springdata.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import springdata.jpa.domain.Post;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

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
    // use-declered 전략
    // native쿼리를 사용하고싶다면 native쿼리를 사용한다.
    //@Query(value = "SELECT c FROM Comment c",nativeQuery = true)
    Page<Post> findByTitleContains(String title, Pageable pageable);

    // counting 쿼리도 만들수있다.
    long countByTitleContains(String title);


    List<Post> findByTitleContainsIgnoreCaseOrderByTitle(String hello_world);

    Stream<Post> findByTitleContains(String title);

    //background 에서 실행되는 스레드풀에 해당 호출메서드의 실행을 위임한다.
    // 해당 코드를 non-blocking 하게 사용하려면 Future를 사용해야한다.
    @Async
    ListenableFuture<List<Post>> findByTitle(String title);
    //Future<List<Post>> findByTitle(String title);

}
