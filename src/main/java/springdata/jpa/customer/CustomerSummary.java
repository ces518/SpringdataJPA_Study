package springdata.jpa.customer;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-06
 * Time: 17:02
 **/

import org.springframework.beans.factory.annotation.Value;

/**
 * Interface 기반의 Projection사용을 위한 Interface
 * Entity의 특정 애트리뷰트에 대한 Getter만 선언해준다.
 */
public interface CustomerSummary {

    int getUp();

    int getDown();


    /*
        JDK 8 이상 부터는 interface에 default 메서드를 사용 할 수 있다.
        Closed Projection을 사용하면서 두개이상의 컬럼을 조합하여 데이터를 가공는방식.
     */
    default String getVotes() {
        return getUp() + " " + getDown();
    }

    /*
      Open Projection 방식
      target = Customer 이며 target의 모든것을 가져와서 처리하기때문에
      Closed Projection과는 상반된 느낌이다.
     */
//    @Value("${target.up + ' ' + target.down}")
//    String getVotes();
}
