package springdata.jpa.customer;

import lombok.*;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-01
 * Time: 21:32
 **/
@Entity @Setter
//@NamedQuery(name="Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password like ?1")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private int up;

    private int down;

    @Builder
    public Customer(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
