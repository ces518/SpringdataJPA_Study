package springdata.jpa.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-04
 * Time: 22:11
 **/
public interface OrderRepository extends JpaRepository<Order,Long> {

    // attributePaths에 설정하는것이 깔끔한방법.
    // 해당부분이 중복되는경우 Entity상단에 정의해놓고 재사용하는것을 추천한다.
    //@EntityGraph(value = "Order.customer")
    @EntityGraph(attributePaths = "customer")
    Optional<Order> getById(Long id);
}
