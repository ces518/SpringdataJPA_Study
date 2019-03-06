package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-05
 * Time: 21:13
 **/

/**
 * 해당클래스가 도메인 클래스라는것을 알려주는 애노테이션
 * Entity클래스에는 해당 클래스의 모든필드를 테이블의 어트리뷰트로 생성한다.
 * @Column이 없더라도 생략한것과 동일한 효과를 가진다.
  */
@Entity
@Getter @Setter
public class Account {

    /**
     * 식별자로 사용하고 , 해당 키값을 자동증가시킨다는설정
     * 자동증가 전략에는 여러가지가있는데,
     * 기본값은 AUTO 임.
     *
     */
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true,length = 200)
    private String username;

    private String password;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Transient
    private String no;

}
