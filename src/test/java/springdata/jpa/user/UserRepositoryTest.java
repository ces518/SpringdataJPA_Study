package springdata.jpa.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void isCreated() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void findByUsername() {
        List<User> users = userRepository.findByUsername("test");

        assertThat(users.size()).isEqualTo(0);
    }
}