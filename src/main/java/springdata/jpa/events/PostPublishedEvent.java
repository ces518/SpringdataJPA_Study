package springdata.jpa.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import springdata.jpa.domain.Post;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-23
 * Time: 19:38
 **/

/**
 * Custom한 Domain Event 정의
 */
@Getter
public class PostPublishedEvent extends ApplicationEvent {

    // Post의 정보를 참조하도록 정의
    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }
}
