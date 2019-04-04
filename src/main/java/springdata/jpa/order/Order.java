package springdata.jpa.order;

import lombok.Getter;
import lombok.Setter;
import springdata.jpa.customer.Customer;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-04
 * Time: 22:05
 **/

/**
 * NamedEntityGraph 에 그룹명을 지정해주고,
 * attributeNodes에 연관관계를 설정해놓은 이름을 지정해준다.
 *
 * 기본값 : FETCH : 설정해놓은 연관관계는 EAGER로 가져오고 ,나머지는 LAZY로 가져온다.
 *          LOAD  : 설정한 연관관계는 EAGER로 가져오고 , 나머지는 기본전략을 따른다.
 *
 * attributeNodes에 설정해 두지않았더라도 기본형 데이터들은 EAGER로 가져온다.
 */
@NamedEntityGraph(name = "Order.customer",
            attributeNodes = @NamedAttributeNode("customer"))
@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * x2one = fetch default EAGER
     * x2many = fetch default LAZY
     */
    @ManyToOne
    private Customer customer;
}
