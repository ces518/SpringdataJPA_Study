package springdata.jpa.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springdata.jpa.events.listener.PostEventListener;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-23
 * Time: 19:44
 **/

/**
 * 테스트를 위한 PostEventListener Bean 등록
 */
@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostEventListener postEventListener() {
        return new PostEventListener();
    }
}
