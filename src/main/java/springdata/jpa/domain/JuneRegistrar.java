package springdata.jpa.domain;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2019-03-13
 * Time: 22:13
 **/

/**
 * 프로그래밍 적으로 빈을 등록할수 있도록 가능캐하는 인터페이스의 구현체.
 * 이러한 구현체가 있기 때문에 , JpaRepository가 자동적으로 빈으로 등록이된다.
 */
public class JuneRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //genericBeanDefinition 을 생성하여 class의 타입을 정의하고 프로퍼티의 값을 세팅한후
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(June.class);
        beanDefinition.getPropertyValues().add("name","june");

        //빈으로 등록
        beanDefinitionRegistry.registerBeanDefinition("june",beanDefinition);
    }
}
