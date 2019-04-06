package springdata.jpa.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customers;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void createCustomer() {
        Customer customer = Customer.builder()
                                    .username("ces518")
                                    .password("pjy3859").build();

        customers.save(customer);

        List<Customer> customerList = customers.findAll();
        assertThat(customerList.size()).isEqualTo(1);
    }

    @Test
    public void save() {
        // transient 상태
        Customer customer = Customer.builder()
//                                    .id(1L)
                                    .username("ces518")
                                    .password("pjy3859").build(); // persist 호출


        Customer savedCustomer = customers.save(customer);// insert Query 발생

        // persist를 호출하면 persist() 메서드의 인자로받은 객체를 PersistentContext에 영속화한다.
        // 즉 인자로받은 객체와 리턴한 객체는 같다.
        assertThat(entityManager.contains(customer)).isTrue();
        assertThat(entityManager.contains(savedCustomer)).isTrue();
        assertThat(customer == savedCustomer);

        Customer customer1 = Customer.builder()
                                    .id(customer.getId())
                                    .username("ces5182")
                                    .password("pjy3852").build(); // merge 호출

        Customer savedCustomer2 = customers.save(customer1);// update Query 발생

        // merge를 호출하면 merge() 메서드의 인자로 받은 객체의 복사본을 만들고,
        // 해당복사본을 PersistentContext에 영속화 한뒤
        // 해당 복사본을 리턴해준다.
        // 인자로받은 객체와 리턴한 객체는 다르다.
        assertThat(entityManager.contains(savedCustomer2)).isTrue();
        assertThat(entityManager.contains(customer1)).isFalse();
        assertThat(customer1 != savedCustomer2);
    }

    @Test
    public void findByUsernameStartsWith() {
        //given
        createCustomer();

        //when
        List<Customer> results = customers.findByUsernameStartsWith("ces518");

        //then
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void findByPassword() {
        //given
        createCustomer();

        //when
        //List<Customer> results = customers.findByPassword("pjy3859", Sort.by(Sort.Direction.DESC,"password"));
        List<Customer> results = customers.findByPassword("pjy3859", JpaSort.unsafe("LENGTH(password)"));
        //then
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void updatePassword () {
        //given
        createCustomer();

        //when
        int result = customers.updateCustomer("pjy38590", 1L);
        Customer customer = customers.findById(1L).get();

        //then
        assertThat(customer.getPassword()).isNotEqualTo("pjy38590");
    }

    @Test
    public void projection() {
        //given
        createCustomer();

        //when
        List<CustomerSummary> list = customers.findByUsername("ces518");

        //then
        assertThat(list.size()).isEqualTo(1);
        System.out.println(list.get(0).getVotes());
    }
}