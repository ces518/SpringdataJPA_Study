package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Long seq;

    @Column(nullable = false,length = 200)
    private String username;

    private String password;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Transient
    private String no;

    // Composite value를 정의한뒤
    // @Embedded 애노테이션 사용하면 해당 value를 컬럼으로 지정이 가능하다.
    // 해당 컴포짓 밸류를 여러개 사용하고싶다면,
    // AttributeOverrides 와, AttributeOverride를 사용하여 컬럼명을 지정해줄수 있다.
    // name과 column은 필수값이다.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name ="address", column = @Column(name = "my_address"))
    })
    private Address address;


    /*
    * Collection type 매핑
    * @ElementCollection 을 정의한다.
    * @CollectionTable 로 (1:N의 관계테이블) 을 생성함.
    * name : 테이블명
    * joinColumns : Account Table의 PK값을 참조할 컬럼
    * @Column 을 사용하여 해당 컬렉션에 속하는 값 (primitive type일 경우에만 )
    *
    * ElementCollection의 경우 해당 Collection에 데이터를 추가할경우,
    * 매번 데이터를 지우고 새로추가해버린다.
    * PK값이 존재하지않기때문에 발생하는 현상임.
    * 이를 줄이기위해 @OrderColumn을 사용할 수 있으나..
    * 문제가 많으므로
    * @OrderBy를 사용하도록하자.
    * */
    @ElementCollection
    @CollectionTable(name = "member_phones", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "phone_number")
    private Set<String> phoneNumbers = new HashSet<>();

    public void addNumber(String s) {
        if(this.phoneNumbers == null) {
            this.phoneNumbers = new HashSet<>();
        }
        this.phoneNumbers.add(s);
    }

    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    /*
    * 양방향 관계에서의 컨비니언트 메서드
    * */
    public void addStudy(Study study) {
        this.studies.add(study);
        study.setOwner(this);
    }

    public void removeStudy(Study study) {
        this.studies.remove(study);
        study.setOwner(null);
    }
}
