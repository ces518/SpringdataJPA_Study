package springdata.jpa.domain;

import javax.persistence.Embeddable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-08
 * Time: 23:21
 **/
// Composite 한 밸류타입 정의
@Embeddable
public class Address {

    private String address;

    private String addressInfo;

    private String zipCode;
}
