package springdata.jpa.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-17
 * Time: 20:55
 **/

/**
 * Custom 한 Repository interface 정의
 * @param <T>
 * @param <Id>
 */
@NoRepositoryBean
public interface MyRepository<T,Id extends Serializable> extends Repository<T, Id> {

    // T의 하위타입도 가능하도록 정의
    <E extends T>E save(E entity);

    List<T> findAll();

}
