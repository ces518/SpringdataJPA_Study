package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-09
 * Time: 19:53
 **/

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    private Long seq;

    private String title;

    /*
      Cascade
    * 부모의 Persistent상태를 자식에게도 전파하는 옵션.
    * Post가 등록되면 comment도 등록,
    * post가 삭제되면 comment 삭제.
    * */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setPost(this);
    }
}
