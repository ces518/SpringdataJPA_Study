package springdata.jpa.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customers;

    @Test
    public void createCustomer() {
        Customer customer = Customer.builder()
                                    .username("ces518")
                                    .password("pjy3859").build();

        customers.save(customer);

        List<Customer> customerList = customers.findAll();
        assertThat(customerList.size()).isEqualTo(1);
    }
}