package springdata.jpa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
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
    public PagedResources<Resource<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toResource(postRepository.findAll(pageable) );
    }
}
