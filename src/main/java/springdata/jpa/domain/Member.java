package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-08
 * Time: 23:31
 **/
/*
* 부모 클래스로부터 상속받은 속성들도 Override할 수 있다.
* */
@Entity
@Getter @Setter
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "member_name"))
})
public class Member extends BaseEntity {

}
