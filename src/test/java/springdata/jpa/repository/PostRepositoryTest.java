package springdata.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import springdata.jpa.domain.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
// repository layer 의 slice test 용 annotation
// 기본적으로 transaction이 rollback
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

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
}