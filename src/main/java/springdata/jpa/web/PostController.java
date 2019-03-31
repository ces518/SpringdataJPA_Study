package springdata.jpa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springdata.jpa.domain.Post;
import springdata.jpa.repository.PostRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-31
 * Time: 21:25
 **/
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
