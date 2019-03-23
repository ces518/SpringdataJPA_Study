package springdata.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import springdata.jpa.domain.Post;
import springdata.jpa.events.PostPublishedEvent;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
// repository layer 의 slice test 용 annotation
// 기본적으로 transaction이 rollback
@DataJpaTest

//test용 config클래스 설정
@Import(PostRepositoryTestConfig.class)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    //rollback false 설정
    @Test
    @Rollback(false)
    public void crudRepository() {
        // given
        Post post = new Post();
        post.setTitle("hello world !");

        // when
        Post save = postRepository.save(post);
        log.info("saved = {}",save.getSeq());

        // then
        assertThat(save.getSeq()).isNotNull();

        // when
        List<Post> all = postRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all).contains(save);

        // when
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
        // paging 관련 정보들 count 등등 이 담겨있음
        List<Post> content = page.getContent();

        // then
        // totalElements = 총 개수
        assertThat(page.getTotalElements()).isEqualTo(1);
        // number = 현재 페이지 넘버
        assertThat(page.getNumber()).isEqualTo(0);
        // size = 페이지당 사이즈
        assertThat(page.getSize()).isEqualTo(10);
        // 현재 페이지의 게시글 수
        assertThat(page.getNumberOfElements()).isEqualTo(1);


        // when
        Page<Post> hellos = postRepository.findByTitleContains("hello", PageRequest.of(0, 10));


        // when
        long hello = postRepository.countByTitleContains("hello");
        assertThat(hello).isEqualTo(1);
    }

    @Test
    public void crud() {
        //given
        createPost("Spring");
        createPost("Hello World");
        createPost("Hibernate");
        createPost("Spring-data-jpa");

        //when
        List<Post> posts = postRepository.findByTitleContainsIgnoreCaseOrderByTitle("Spring");

        //then
        assertThat(posts.size()).isEqualTo(2);
        // 해당 필드명과 , 벨류값을 지정하여 테스트
        assertThat(posts).first().hasFieldOrPropertyWithValue("title","Spring");
    }

    @Test
    public void page() throws ExecutionException, InterruptedException {
        //given
        createPost("Spring");
        createPost("Hello World");
        createPost("Hibernate");
        createPost("Spring-data-jpa");

        //when
        //static factory method 사용
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"title"));

        Page<Post> posts = postRepository.findByTitleContains("Spring", pageRequest);

        /*
        * Stream을 사용할 경우 try with resource문을 활용하여
        * 사용후 반드시 닫아주어야한다.
        * */
        try(Stream<Post> postStream = postRepository.findByTitleContains("Spring")){
            Post firstPost = postStream.findFirst().get();
            assertThat(firstPost.getTitle()).isEqualTo("Spring");
        }


        // Future 는 1.5에 추가된 기능이다.
        ListenableFuture<List<Post>> spring = postRepository.findByTitle("Spring");

        // 호출이 끝났는지 확인
        spring.isDone();
        spring.addCallback(new ListenableFutureCallback<List<Post>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onSuccess(@Nullable List<Post> posts) {
                System.out.println("success");
            }
        });
        // parameter가 존재하지않으면 , 결과를 받아올때까지 무작정 기다리고;
        List<Post> postList = spring.get();
        postList.forEach(System.out::println);
        // spring.get(....); 파라미터가 존재하면 정해진 시간만큼만 대기한다.



        //then
        assertThat(posts.getNumberOfElements()).isEqualTo(2);
    }

    private void createPost(String title) {
        Post post = new Post();
        post.setTitle(title);
        postRepository.save(post);
    }

    @Test
    public void events() {
        Post post = new Post();
        post.setTitle("new Events");

        this.applicationContext.publishEvent(new PostPublishedEvent(post));

        // 이벤트 리스너를 등록해주어야함.
        // spring data jpa는 save될때 이벤트를 publishing 해주는 기능을 제공한다.
        // AbstractAggregationRoot 라는 클래스에 이미 구현이 되어있다.
        // 해당 도메인 클래스에 상속을받아 구현을 해주면 된다.
        // 그럼 위의 코드는 필요없이 save할때마다 자동적으로 이벤트가 발생함.

        Post post2 = new Post();
        post2.setTitle("events auto");
        this.postRepository.save(post2.publish());
    }

}