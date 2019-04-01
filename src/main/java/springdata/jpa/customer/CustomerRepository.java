package springdata.jpa.customer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-01
 * Time: 21:33
 **/
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
