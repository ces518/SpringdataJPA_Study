package springdata.jpa.domain;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-08
 * Time: 23:30
 **/
// 부모클래스로 정의하여 자식에게 전해줄수있으며 엔티티로 등록하지않았기때문에 테이블이 되지않음.
@MappedSuperclass
@Getter
public class BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

}
