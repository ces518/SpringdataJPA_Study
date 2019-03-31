package springdata.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-20
 * Time: 22:07
 **/

//queryDsl 을 사용하려면 , SimpleJpaRepository가 아닌
//QuerydslJpaRepository 를 상속받아야한다.
//public class JuneRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements JuneRepository<T,ID> {
public class JuneRepositoryImpl<T,ID extends Serializable> extends QuerydslJpaRepository<T,ID> implements JuneRepository<T,ID> {
    private EntityManager entityManager;

    public JuneRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

//    public JuneRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//        this.entityManager = entityManager;
//    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }
}
