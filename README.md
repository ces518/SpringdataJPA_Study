## JPA

### ORM이란 ?
> 애플리케이션의 클래스와 SQL 데이터베이스 테이블 간에 , 맵핑정보를 포함한  메타데이터를 기술하여 
> 자바 애플리케이션의 객체를 SQL데이터베이스에 자동으로 (또 깨끗하게) 영속화 해주는 기술이다.


### ORM 을 사용해야하는 이유 ?
> JDBC기반의 코딩은 너무 코드가 장황하고 , 불필요한 중복코드가많다.
> 도메인 모델 기반으로 코딩을 할경우 , 이를해결하고 , 객체지향 프로그래밍 , 각종디자인패턴 , 코드재사용 , 비지니스로직에 집중 및 테스트가 편리해진다.

### ORM을 사용할 경우 장점 ? 
1. 생산성
2. 유지보수성
3. 성능
4. 벤더 독립성

### 단점 ? 
- 학습비용이 크다.


### ORM 패러다임의 불일치 ?
- 객체를 릴레이션에 매핑하려니 발생하는 문제와 해결방법들 

1. 밀도 문제

| 객체 | 릴레이션 | 
| :---|:---|
|다양한 크기의 객체를 만들기쉬|테이블|
|커스텀한타입을 가질수있음 | 기본데이터타입|

> 커스텀한 데이터타입을 만들었을떄 ORM이 릴레이션과 매핑문제를 해결해준다.

2. 서브타입 문제

- 객체는 상속구조를 만들기가쉽다.
- 테이블은 상속구조가 없다.
- 다형성
- 다형성을 표현할 방법이없다.

3.식별성 문제

- 레퍼런스 동일성
- 주키
- 인스턴스의 동일성 equals
> 식별성문제를 ORM이 해결해준다.
4. 관계문제

- 객체에서는 레퍼런스 관계로 표현된다.
- 근본적으로 '방향' 이 존재한다.
- 다대다 관계를 가질 수 있다.

- 외래키로 관계를 표현한다.
- 방향이라는 의미가없다, join으로 인해 아무방향이나 다 가져올수있다.
- 다대다 관계가없으며 조인테이블이나 링크테이블이 필요하다.
> 이러한 매핑문제를 ORM이 해결해준다.
> 링크테이블을 자동적으로 생성해주는등..

5. 데이터 네비게이션문제

- 객체에서는 레퍼런스를 이용하여 다른객체로 이동이 가능하다.
- 콜랙션을 순회할 수도있다.

- 하지만 위의 방식은 릴레이션에서는 매우 비효율적이다.
- 요청을 적게 할 수록 효율이 좋기때문에 JOIN을 사용한다.
- 하지만 한번에 많은 수의 데이터를 가져오더라도 문제가된다.
- LAZY LOADING 방식을 사용하려고해도 문제이다. N+1 SELECT 
> JDBC 커넥션을 사용하는것이 비용자체가 크다.
> 가급적이면 한 트랜잭션에서 요청을 처리하는것이 성능면에서 유리하다.


### JPA 자동설정 ?
- HibernateJPAAutoConfiguration
- 클래스에서 PlatformTransactionManager, EntityManagerFactoryBean등 을 자동으로 주입해준다.
- 그외에도 JPA를 사용하기 위한 다양한 설정들을 자동으로해준다.

> createClob() 이라는 경고메시지가 발생할경우 (postgres)
> 아래의 설정을추가하면 경고메시지가 사라진다.
> 해당 메시지가 나는이유 ? 
- 사용하는 드라이버가 createClob을 구현하지않았기때문에 발생함.
- spring.jpa.properties.hibernate.jdbc.lob.non_context_creation=true


### 엔티티 매핑
> @Entity
- 엔티티는 객체지향프로그래밍에서의 이름이다.
- 보통 클래스와 동일한 이름을 사용하기때문에 변경하지않는다.
- 엔티티의 이름은 JQL에서 사용한다.

> @Table
- 릴레이션 (데이터베이스) 에서의 이름이다. [실제 테이블의 이름]
- @Entity의 이름이 기본값이다.
- 테이블의 이름은 SQL에서 사용된다.

> @Id
- 엔티티의 주 키를 매핑할때 사용한다.
- 자바의 모든 primitive타입과 , 래퍼타입을 사용할 수 있다.
- Date, BigDecimal, BigInteger도 사용이 가능하다.
- 복합키를 만드는 매핑방법도 존재한다.

> @GeneratedValue
- 주키의 생성 방법을 매핑하는 애노테이션이다.
- 생성전략과 , 생성기를 설정할 수 있다.
    - 기본전략은 AUTO : 사용하는 디비에 따라 적절한 전략을 선택한다.
    - TABLE, SEQUENCE, IDENTITY중 하나 ..

> @Column
- 테이블의 컬럼을 매핑할때 사용한다.
- 엔티티 클래스의 필드에 사용한다.
- unique. nullable, length,columnDefiniton... 등 다양한 옵션을 제공한다.

> @Temporal
- 현재 JPA2.1까지는 Date, Calender한다. 2.2 이상부터 LocalDate지원..
- Custom한 타입을 매핑하는 방법도 존재한다. (Converter)

> @Transient
- 컬럼과 매핑하고싶지않은 필드에 사용한다.

### VALUE 타입 매핑 

>엔티티타입과 벨류타입 
- 식별자가 존재하는가
- 독립적으로 존재해야하는가

>밸류타입 종류
- primitive type
- Composite type
- Collection Value type
    - 기본타입의 콜렉션
    - 컴포짓 타입의 콜렉션

> 컴포짓 벨류타입 매핑
- @Embadable
- @Embadded
- @AttributeOverrides
- @AttributeOverride

### 1:N 매핑
- 관계에는 항상 두 엔티티가 존재한다.
    - 둘중 하나는 그 관계의 주인 (owner) 이고
    - 다른쪽은 종속된 쪽 (non-owner) 이다.
    - 해당관계의 반대쪽 레퍼런스를 참조하고 있는 엔티티가 관계의 주인이다.
    
- 단방향 관계 매핑
    - 단방향 관계에서의 주인은 명확하다.
    - 관계를 정의한쪽이 그 주인이다.
    
- 단방향 @ManyToOne
    - 기본값은 FK 컬럼 생성
    - 기본 Fetch 전략은 EAGER

- 단방향 @OneToMany
    - 기본값은 조인테이블 생성
    - 기본 Fetch 전략은 LAZY
    
- 양방향 
    - FK를 가지고있는쪽이 관계의 주인이다.
    - 따라서 기본값은 @ManyToOne의 관계를 정의한쪽이 관계의 주인이된다.
    - 관계의 주인이 아닌쪽에서는 @OneToMany 쪽에서 mappedBy를 사용하여 관계를 맺고있는 필드를 설정해야한다.
    - 주인에게 관계를 설정해야 DB에 반영이된다.
    
### Cascade
- 엔티티의 상태를 전파시키는 옵션

> 엔티티의 상태란 ?
- Transient : JPA가 모르는상태
- Persistent : JPA가 관리중인 상태 (1차캐시,Dirty Checking , Write Behind..)
- Detached : JPA가 더이상 관리하지않는 상태
- Removed : JPA가 관리하긴하지만 삭제하기로한 상태

> 1차 캐시란?
-  save를 호출했다고 해서 INSERT QUERY가 바로 발생하는것이 아니라.
          Persistent Context객체에 영속화 되었다가
          Transaction이 종료되는 시점에 Insert Query가 발생한다.
          즉, save를 한뒤 다시 로드하더라도, select쿼리는 발생하지않는다.
    
> Persistent 상태
- JPA가 관리하는 Persistent상태가 되면 , JPA가 게속해서 객체의 변경을 감지하는 상태이다.
- 즉, 해당 객체에 변경사항이 일어나면, 이를 감지하고 자동적으로 반영해준다 (Update) 

> Dirty Checking
- 객체의 상태를 지속적으로 감지

> Write Behind
- 객체를 최대한 늦게, 필요한 시점에 DateBase에 적용

> Detached 상태
- Service 나 Repository의 트랜잭션이 종료된 후, Controller로 해당 객체를 전달해주었을때
- 해당 트랜잭션 (Hibernate Session) 이 종료되었기때문에 JPA의1차캐시,DirtyChecking, Wrtie Behind, LAZY Loading등이 일어나지않는다.
- Persistent상태로 돌아가려면 reAttach를 해야함.

> cascade 
- 부모의 상태를 자식에게도 전파한다.
- 일반적으로 CascadeType.ALL 을 사용함.
- 부모가 등록되면 자식도 자동적으로 등록되며, 삭제시에도 동일하다.


### Fetch
- 연관관계의 데이터를 어떻게 가져올것인가 ? ..
- 기본값.
- OneToMany : LAZY 
- ManyToOne : EAGER 

> LAZY ? 
- 지연로딩 
- 1:N의 관계일때 , 1에 해당하는 데이터를 읽어올때 N에 해당하는 데이터들을 읽어오지않고,
- N에 해당하는 데이터를 사용할때 그제서야 가져오는것

> EAGER ?
- 즉시로딩
- 1:N의 관계일떄, 1에 해당하는 데이터를 읽어올때, N에 해당하는 데이터들을 같이 읽어온다.
 

### JPA Query ?
> JPQL (HQL)
- Java Persistence Query Language / Hibernate Query Language
- 데이터 베이스기반 쿼리가아닌, 엔티티기반 쿼리.
- JPA 또는 하이버네이트가 해당쿼리를 SQL 로 변환하여 실행한다.
- JPA 2.0 까지는 Type쿼리를 사용할수없다.
```
       TypedQuery<Post> query = entityManager.createQuery("select p from Post p", Post.class);
        List<Post> posts = query.getResultList();
        posts.forEach(System.out::println);

```
> 단점
- 문자열기반이기 때문에 오타 등 타입세이프하지않다.

> TypeSafe한 쿼리
- Criteria
```
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query2 = criteriaBuilder.createQuery(Post.class);
        Root<Post> from = query2.from(Post.class);
        query2.select(from);

        List<Post> resultList = entityManager.createQuery(query2).getResultList();
        resultList.forEach(System.out::println);
```
- typeSafe하다는 장점이 있지만, 역시 잘사용하지 않는다.

> NamedQuery
- Mybatis 처럼 쿼리를 미리 등록해두고 사용하는 방법도 존재한다.
- 엔티티 상단에 미리 쿼리를 등록해 두어야한다.
```
  TypedQuery<Post> getAllPosts = entityManager.createNamedQuery("getAllPosts", Post.class);
```

> NatvieQuery
- 직접 Native한 SQL 을 작성하여 사용할 수있다.
- 한 가지 재미있는점은 returnType을 지정할 수 있음에도 불구하고
- 해당 메서드의 리턴타입은 Query형태로만 반환한다.
- 하지만 받아올때 Generic을 사용하여 받아올수는 있다.

### Spring-data-jpa 의 원리
- Repository를 직접 매번 일일히 구현하기엔 매우 번거로운 작업이기에
- 불과 7~8년 전만해도 GenericRepository<Board,Long> 의 형태로 자주쓰는 케이스를 프레임워크형태로 쓰는것이 유행이었다.
```
@Repository
public class BoardRepository extends GenericRepository<Board,Long> {
    ...
}
```

- 최근의 가장 진보된 형태의 방법
- JpaRepository<Entity,Id> 형태의 인터페이스를 상속받는 방법.
- @Repository가 없어도 빈으로 등록을 해준다.

- @EnableJpaRepositories 애노테이션을 @Configuration class에 선언해주어야하지만
- Spring Boot의 경우에는 이를 자동적으로 해준다.

> 내부적으로 어떤식으로 이루어지는지 ?
- @Import(JpaRepositoriesRegistrar.class) 로 부터 시작
- 핵심은 ImportBeanDefinitionRegistrar 인터페이스
- ImportBeanDefinitionRegistrar 는 스프링 프레임웍이 제공해주는 인터페이스이다.
> Bean을 프로그래밍을 통하여 빈을 생성할수 있도록 제공함.
- 특정 interface를 상속받은 클래스를 빈으로 자동등록하도록 이 가능해진다.

### Spring-data-common
- spring-data Project의 공통 프로젝트 
- PagingAndSortingRepository (paging , sorting 제공 )
- CrudReposistory (기본 crud)
- Repository (마커 interface)
- @NoRepositoryBean (실제 레포지토리가 아니므로 빈으로 등록하지않도록 하는 애노테이션)

- save (저장)
- saveAll (다수 저장)
- findById (1개 검색)
- existById (존재여부 판단)
- 등등 ...


### Repository 인터페이스 정의하기
- 인터페이스로 공개할 메서드를 정의하고싶다면 ..
- @RepositoryDefinition 애노테이션을 사용하여 정의할 수 있다.
- 하지만 위의 방법은 매번 Repository마다 새로 정의 해 주어야하기때문에 번거롭다.
- @NoBeanRepository 를 사용하여 Repository interface만 상속하는 형태로 정의한다면 
- 매번 정의해주지 않아도 된다.
```
//@RepositoryDefinition(domainClass = Comment.class,idClass = Long.class)
public interface CustomRepository extends MyRepository<Comment,Long>{

//    Comment save(Comment comment);

//    List<Comment> findAll();
}
```
```
@NoRepositoryBean
public interface MyRepository<T,Id extends Serializable> extends Repository<T, Id> {

    // T의 하위타입도 가능하도록 정의
    <E extends T>E save(E entity);

    List<T> findAll();

}
```



