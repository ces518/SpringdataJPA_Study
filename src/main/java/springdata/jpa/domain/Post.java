package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import springdata.jpa.events.PostPublishedEvent;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-09
 * Time: 19:53
 **/

@Entity
@Getter @Setter
@NamedQueries({
        @NamedQuery(name = "getAllPosts", query = "SELECT p FROM Post p")
})
public class Post extends AbstractAggregateRoot<Post> {

    @Id @GeneratedValue
    private Long seq;

    private String title;

    private String created;

    /*
      Cascade
    * 부모의 Persistent상태를 자식에게도 전파하는 옵션.
    * Post가 등록되면 comment도 등록,
    * post가 삭제되면 comment 삭제.
    * */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setPost(this);
    }

    @Override
    public String toString() {
        return "Post{" +
                "seq=" + seq +
                ", title='" + title + '\'' +
                ", comments=" + comments +
                '}';
    }

    // spring data jpa 가 제공해주는 publishing 기능을 사용하기위한 구현
    public Post publish() {
        registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
