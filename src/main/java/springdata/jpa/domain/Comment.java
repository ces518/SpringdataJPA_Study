package springdata.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-09
 * Time: 19:55
 **/
@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    private Long seq;

    private String comment;

    @ManyToOne
    private Post post;
}
