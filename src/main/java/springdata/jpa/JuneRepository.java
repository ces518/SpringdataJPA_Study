package springdata.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 22:05
 **/

/**
 * 중간 단계의 Repository는 NoRepositoryBean을 반드시 붙여주어야한다.
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface JuneRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {

    boolean contains(T entity);
}
