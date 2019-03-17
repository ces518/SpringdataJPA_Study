package springdata.jpa.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    @Nullable
    <E extends T> Optional<T> findById(@NonNull Id id);
}
