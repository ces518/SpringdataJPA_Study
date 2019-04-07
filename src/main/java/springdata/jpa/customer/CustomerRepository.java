package springdata.jpa.customer;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-01
 * Time: 21:33
 **/
public interface CustomerRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

    List<Customer> findByUsernameStartsWith(String username);

    //Customer.findByPassword
    //method명을 키로사용하여 NamedQuery를 찾는다.
    //NamedQuery를 사용하면 Domain Class가 지저분해짐.
    @Query("SELECT c FROM Customer c WHERE c.password like :password")
    List<Customer> findByPassword(String password, Sort sort);

    //NamedParameter를 사용하면 해당 파라미터의 이름과 매핑을 해주며,
    //변수명이 꼭 파라미터명과 같지않아도 동작한다.
    //SpEL 지원 SpringExpressionLanguage를 지원한다.
    //EntityNamed변수를 기본적으로 제공하며 해당 엔티티클래스에서 엔티티명이 변경되어도
    //@Query 애노테이션을 사용한 곳에서 수정해주지않아도 된다는 장점이 있다.
    @Query("SELECT c FROM #{#entityName} c WHERE c.password like :password")
    List<Customer> findByPassword2(@Param("password") String password);

    //UPDATE Query를 직접 정의하여 사용할경우 데이터 싱크가 맞지않을 수 있다.
    //Update후 바로 select하여 사용할 경우 PersistenceContext에 캐싱된 객체를 사용하기때문.
    //Spring에서도 이 문제를 알고 , persistenceContext를 clear해주는 옵션을 제공한다.
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password = ?1 WHERE c.id = ?2")
    int updateCustomer(String password, Long id);


    /*
      closed 방식
      Interface 기반 Projection 사용
     */
    //List<Customer> findByUsername(String username);
    List<CustomerSummary> findByUsername(String username);

    /*
        Generic을 활용하여 다양한 Projection 사용시 쿼리메서드 하나로 사용 할 수 있다.
     */
    <T> List<T> findByUsername(String username, Class<T> type);
}
