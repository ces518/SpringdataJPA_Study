package springdata.jpa.customer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-01
 * Time: 21:32
 **/
@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Builder
    public Customer(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
