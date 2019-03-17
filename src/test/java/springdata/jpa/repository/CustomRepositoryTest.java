package springdata.jpa.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import springdata.jpa.domain.Comment;
import springdata.jpa.domain.Post;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomRepositoryTest {

    @Autowired
    CustomRepository customRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(false)
    public void save() {
        //given
        Post post = new Post();
        post.setTitle("hell world");

        Comment comment = new Comment();
        comment.setComment("hello comment");
        comment.setPost(post);

        // when
        Comment save = customRepository.save(comment);

        // then
        assertThat(save).isNotNull();

        // when
        List<Comment> all = customRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void findById() {
        // when
        Optional<Comment> byId = customRepository.findById(100L);
        assertThat(byId).isEmpty();

        // 값이 없을경우 빈 인스턴스를 반환 해준ㅁ다.
        Comment comment = byId.orElse(new Comment());

        // 예외도 던질수 있다.
        byId.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void findAll() {
        // collection Type들은 비어있는 collection을 리턴해준다.
        List<Comment> all = customRepository.findAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(0);

    }
}