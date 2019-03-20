package springdata.jpa.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 21:24
 **/
@Entity
@Getter @Setter @NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    //data 의 크기가 varchar의 크기를 넘어가는경우 lob으로 설정가능
    @Lob
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
