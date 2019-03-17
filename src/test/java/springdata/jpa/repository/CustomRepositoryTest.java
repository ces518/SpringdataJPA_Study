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

}