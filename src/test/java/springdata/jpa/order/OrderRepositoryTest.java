package springdata.jpa.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orders;


    @Test
    public void fetch() {
        Optional<Order> byId = orders.findById(1L);

        Optional<Order> order = orders.getById(1L);
    }
}