package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-09
 * Time: 16:06
 **/
@Entity
@Getter @Setter
public class Study {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /*
    * 1명의 account가 여러개의 스터디를 가질수있으므로
    * ManyToOne
    * 1:N관계를 지정할 경우 ,
    * 관계의 대상이 되는 엔티티의 PK값을 참조키로 생성한다.
    * 기본 전략은 해당 엔티티의 변수명 (owner) + 해당 엔티티의 id 명
    *
    * 관계의 주인은 Study가 된다 .
    *
    * 왜 ?
    * ManyToOne으로 해당 Account를 참조하고 있기때문에
    * owner의 정보를 가지고있다.
    *
    * */
    @ManyToOne
    private Account owner;

}
