package springdata.jpa.customer;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-07
 * Time: 18:40
 **/
public class CustomerSpecs {

    /*
      Spec 정의
      Customer의 up이 10보다 큰경우
    * */
    public static Specification<Customer> isGood() {
        return new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {
                return builder.greaterThan(root.get(Customer_.up),10);
            }
        };
    }
    /*
      Spec 정의
      Customer의 down 이 10보다 큰경우
    * */
    public static Specification<Customer> isBad() {
        return new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {
                return builder.greaterThan(root.get(Customer_.down),10);
            }
        };
    }
}
