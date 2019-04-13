package springdata.jpa;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import springdata.jpa.domain.Account;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-04-13
 * Time: 21:41
 **/
@Service
public class AccountAuditorAware implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
//          Spring Security를 사용할경우 구현
//        Authentication authentication = SecurityContextHolder.getAuthentication();
//        if(authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//        return Optional.of(authentication);
    }
}
