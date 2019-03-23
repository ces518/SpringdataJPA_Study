package springdata.jpa.events.listener;

import org.springframework.context.ApplicationListener;
import springdata.jpa.events.PostPublishedEvent;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-23
 * Time: 19:43
 **/

/**
 * 이벤트가 발생했을때 처리를 할 리스너를 등록.
 * ApplicationListener를 구현함.
 * 빈으로 등록되어 있어야한다.
 *
 */
public class PostEventListener implements ApplicationListener<PostPublishedEvent> {
    @Override
    public void onApplicationEvent(PostPublishedEvent postPublishedEvent) {
        System.out.println(postPublishedEvent.getPost().getTitle());
    }
}
