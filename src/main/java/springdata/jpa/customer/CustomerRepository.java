package springdata.jpa.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-01
 * Time: 21:33
 **/
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByUsernameStartsWith(String username);

    //Customer.findByPassword
    //method명을 키로사용하여 NamedQuery를 찾는다.
    //NamedQuery를 사용하면 Domain Class가 지저분해짐.
    @Query("SELECT c FROM Customer c WHERE c.password like :password")
    List<Customer> findByPassword(String password);
}
