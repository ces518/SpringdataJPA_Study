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
```JAVA
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

### Null 처리하기
- return 값이 하나 일 경우 java 8 의 Optional을 사용 하여 null 처리 가능하다.
- Collection 들은 비어있는 collection 을 리턴하기때문에 null check 가 의미없다.
- Spring Framework 5.0 부터 지원하는 null 애노테이션 을 지원한다.
- @NonNullApi @NonNull @Nullable 
- 런타임 체크 지원
- JSR 305 애노테이션 을 메타 애노테이션을 지원함.


### Query 만들기
- 스프링 데이터 저장소에 쿼리를 만들기 
- 메서드명 분석해서 생성 (CREATE)
```
Page<Post> findByTitleContains(String title, Pageable pageable);
```
- 미리 정의해둔 쿼리를 찾아 사용 (USE_DECLARED_QUERY)
```
// use-declered 전략
// native쿼리를 사용하고싶다면 native쿼리를 사용한다.
@Query(value = "SELECT c FROM Comment c",nativeQuery = true)
```
- 미리 정의한 쿼리를 찾아보고 없으면 만들기 (CREATE_IF_NOT_FOUND) [기본전략]
```
// Query생성 전략을 설정해줄수있다CREATE_IF_NOT_FOUND가 기본전략
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
```

> 쿼리를 찾는방법
- @Query , @NamedQuery ... 
- 를 사용하는데 JpaRepository의 경우에는 @Query, 프로시저 , 네임드쿼리 순으로 우선순위를 가진다.

- 리턴타입
- Collection , Composit , Page , Optional , Domain 객체 등이 올수있다.

> 쿼리 생성 전략 ? 
- 가장먼저 메서드명으로 쿼리를 생성해본다.
- 다음 차선책으로 @Query 사용 

> Stream<T>
```
        /*
        * Stream을 사용할 경우 try with resource문을 활용하여
        * 사용후 반드시 닫아주어야한다.
        * */
        try(Stream<Post> postStream = postRepository.findByTitleContains("Spring")){
            Post firstPost = postStream.findFirst().get();
            assertThat(firstPost.getTitle()).isEqualTo("Spring");
        }
```

> 비동기 쿼리 (권장하는 기능은 아님 ..)
- repository layer에서 @Async는 제대로 동작하지않음..
- ListenerableFuture를 사용하면 callback을 등록하여 가능하긴하다.
```
    //background 에서 실행되는 스레드풀에 해당 호출메서드의 실행을 위임한다.
    // 해당 코드를 non-blocking 하게 사용하려면 Future를 사용해야한다.
    @Async
    Future<List<Post>> findByTitle(String title);
    
    // Future 는 1.5에 추가된 기능이다.
    Future<List<Post>> spring = postRepository.findByTitle("Spring");

    // 호출이 끝났는지 확인
    spring.isDone();
    // parameter가 존재하지않으면 , 결과를 받아올때까지 무작정 기다리고;
    List<Post> postList = spring.get();
    postList.forEach(System.out::println);
    // spring.get(....); 파라미터가 존재하면 정해진 시간만큼만 대기한다.
```
### 비동기 처리는 Spring5 WebFlux + mongodb 조합을 사용할것..



### Custom Repository 생성하기
- 쿼리메서드로 해결이 되지않는경우 , 직접 코딩으로 구현이 가능하다.
- 스프링데이터 리포지토리 인터페이스 기능추가
- 기본기능 덮어쓰기 가능

- 구현방법
    - 커스텀 리포지토리 인터페이스 정의
    - 인터페이스 구현 클래스만들기 (..Impl)
    - 엔티티 리포지토리에 커스텀 리포지토리 인터페이스 추가

- 개발자가 구현한 Custom한 구현체를 우선순위를 높게주기 때문에
- 기존에 JPA가 제공해주는 기능과 동일한 네임의 메서드가 존재할경우
- Custom한 구현체가 우선순위를 가지므로 해당 구현체의 메서드가 실행된다.
    
> JPA 에서의 delete
- 삭제하려는 대상 엔티티가 persistent context의 관리대상이아니라면(detached) 
- merge를 호출하여 다시 persist 상태로 만든뒤 , remove를 실행한다

> 그이유는 ? 
- 만약에 해당 엔티티가 삭제되면 그 엔티티와 관계를 맺고있는 다른 엔티티에도 영향이 가능경우 (Casecade)

> 접미어 설정하기
- custom 한 repository의 구현체는 Impl이 기본 접미어이다.
- 접미어를 설정하는 방법
```
@EnableJpaRepositories({
repositoryImplementationPostfix = "TEST"})
```

### 기본 리포지토리 커스터마이징 (모든 엔티티 리포지토리에 적용)
- JpaRepository를 상속받는 리포지토리를 정의
- 기본 구현체를 상속받는 리포지토리 구현체 정의
- @EnableJpaRepositories(repositoryBaseClass) 에 추가
- Custom한 Repository를 상속받아 사용하면된다.
```
@NoRepositoryBean
public interface JuneRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {

    boolean contains(T entity);
}.l,

public class JuneRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements JuneRepository<T,ID> {

    private EntityManager entityManager;

    public JuneRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }
}
```

### 도메인 이벤트
- 이벤트 기반의 프로그래밍이 가능하게 할수있다.
- ApplicationContext는 단순한 BeanFactory가아니라 , 이벤트 퍼블리셔이다.
- ApplicationContext를 활용하여 Event를 Publish 할수있다.

```
Post post = new Post();
post.setTitle("new Events");

this.applicationContext.publishEvent(new PostPublishedEvent(post));
```

- 해당 도메인에 대한 이벤트를 발생시키기 전에 
- ApplicationEvent를 상속받는 구현체 정의가필요하다.

```
/**
 * Custom한 Domain Event 정의
 */
@Getter
public class PostPublishedEvent extends ApplicationEvent {

    // Post의 정보를 참조하도록 정의
    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }
}
```

- 구현체를 정의한 다음 , 해당 이벤트 발생시 처리할 리스너를 정의해주어야한다.

```
/**
 * 이벤트가 발생했을때 처리를 할 리스너를 등록.
 * ApplicationListener를 구현함.
 * 빈으로 등록되어 있어야한다.
 *
 */
public class PostEventListener implements ApplicationListener<PostPublishedEvent> {
    @Override
    public void onApplicationEvent(PostPublishedEvent postPublishedEvent) {
        System.out.println(postPublishedEvent.getPost().getTitle());
    }
}
```

- 위의 방법 외에도 Spring data jpa는 event Publish 기능을 제공한다.
- 먼저 domain class에 bstractAggregateRoot 를 상속받아 이벤트 publishing 메서드를 구현해주어야한다.

```
// spring data jpa 가 제공해주는 publishing 기능을 사용하기위한 구현
public Post publish() {
    registerEvent(new PostPublishedEvent(this));
    return this;
}
```
- 해당 부분이 구현 되어있다면 repository를 통해 save될때 자동적으로 이벤트가 publish 된다.

```
Post post2 = new Post();
post2.setTitle("events auto");
this.postRepository.save(post2.publish());
```
 
 ### Query DSL
> Query DSL 사용이유 ?
- type safe 하다 . java code로 조건문을 표현할 수 있다.
- Predicate 인터페이스로 조건문을 관리하는데 조합도 가능하며 , 따로 관리도 할 수 있다.
- QueryDslPredicateExecutor 를 jpa가 제공한다.
    - findOne
    - findAll 
    - 위 두메서드를 지원한다.
    
> 연동 방법 ?  
- 기본 리포지토리를 사용하는경우.
- 의존성 과 플러그인을 추가한다.
```
<!--
    querydsl은 spring boot가 의존성을 관리하기때문에
    추가적인 설정이 필요없다.
    apt 모듈은 코드를 생성해주는 모듈이다.
-->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
</dependency>

<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
</dependency>

<!--querydsl maven plugin 사용용-->
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <goals>
                <!--process lifecycle에 적용-->
                <goal>process</goal>
            </goals>
            <configuration>
                <!-- java 패키지 및에 생성-->
                <outputDirectory>target/generated-sources/java</outputDirectory>
                <!-- 해당 처리 클래스 설정-->
                <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                <!--모든 설정 후 maven 에 compile cycle을 실행-->
            </configuration>
        </execution>
    </executions>
</plugin>
```
```
            <!--querydsl maven plugin 사용용-->
            <plugin>
                <groupId>com.mysema.maven</groupId>
                <artifactId>apt-maven-plugin</artifactId>
                <version>1.1.3</version>
                <executions>
                    <execution>
                        <goals>
                            <!--process lifecycle에 적용-->
                            <goal>process</goal>
                        </goals>
                        <configuration>
                            <!-- java 패키지 및에 생성-->
                            <outputDirectory>target/generated-sources/java</outputDirectory>
                            <!-- 해당 처리 클래스 설정-->
                            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                            <!--모든 설정 후 maven 에 compile cycle을 실행-->
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

- querydsl을 사용할 리포지토리에 QuerydslPredicateExecutor 상속받는다.

```
/**
 * QuerydslPredicateExecutor 를 추가적으로 상속받는다.
 */
public interface AccountRepository extends JpaRepository<Account,Long>, QuerydslPredicateExecutor<Account> {
}
```

- 커스텀한 리포지토리를 사용 할 경우
- SimpeJpaRepository가 아닌 QuerydslJpaRepository를 상속받는다.

- 그 이유는 ? 
- QuerydslJpaRepository 는 SimpleJpaRepository를 상속받아 구현을하는데
- CustomRepository를 구현하여 사용할경우엔 해당 메서드가 구현이 되어있지않음. 
```
//queryDsl 을 사용하려면 , SimpleJpaRepository가 아닌
//QuerydslJpaRepository 를 상속받아야한다.
//public class JuneRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements JuneRepository<T,ID> {
public class JuneRepositoryImpl<T,ID extends Serializable> extends QuerydslJpaRepository<T,ID> implements JuneRepository<T,ID> {
    private EntityManager entityManager;

    public JuneRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager
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
``` 

- 사용방법
1. 의존성과 플러그인을 모두 추가해준다.
2. maven lifecycle > compile을 실행한다.
3. outputDirectory에 생성이 되었는지 확인한다.
4. Predicate를 활용하여 쿼리를 생성한다.

```
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void test() {

        QAccount account = QAccount.account;
        // predicate 를 활용 조건문 생성
        Predicate predicate = account.username.likeIgnoreCase("june")
                            .and(account.username.startsWithIgnoreCase("june"));

        Optional<Account> result = accountRepository.findOne(predicate);

        assertThat(result).isEmpty();
    }
}
```

### Spring data common 이 제공하는 web지원 기능
- 제공기능 
    - 도메인클래스 컨버터
    - 요청 매개변수를 Pageable , Sort로 변환하여 받을 수 있다.
    - Page 관련 HATEOAS 기능 제공
        - PagedResource
        - PagedResourceAssembler
        - 페이지에 대한 정보도 같이 내 보내준다.
    - payload 프로덕션
        - 요청으로 들어온 정보의 일부만 캡쳐하여
        - 바인딩 할 수 있다.
        - @ProjectedPayload 애노테이션이 붙은 인터페이스를 정의하고
        - @JsonPath , @XBRead 를 통해 설정해줄 수 있다.
        - 해당 인터페이스 타입으로 @RequestBody를 통해 받아오면 된다.
    - 요청 쿼리 매개변수를 QueryDsl 의 Predicate로 받아올 수 있다.
        - firstName=test&lastName=who
        - QAccount.account.firstName.eq("test").and(QAccount.account.lastName.eq("who"));
        - 의 형태로 받아 올 수 있다.
            
        
### Domain Class Converter
- EntityConverter 와 IdConverter가 등록되어있으며
- WebDataBinder가 이를 참조하여 활용함.

### Formatter와 Converter의 차이 ?
- Formatter는 문자열 기반이다.
- 문자열을 다른타입으로 변환
- 웹에 좀더 특화되어있다.

- Converter 는 모든타입을 제공한다.
- 특정타입을 다른 타입으로 변환

### Pagable 과 Sort 매개변수
- 스프링 MVC HandlerMethodArgumentResolver
    - 스프링 MVC의 핸들러 메서드의 매개변수로 받을 수있는 객체들을 확장하고 싶을때 사용하는 인터페이스

- 페이징과 정렬관련 기본 매개변수
    - page: 0부터 시작.
    - size : 기본값 20
    - sort: property,ASC||DESC
    - ex) sort=title,desc
```
@GetMapping("/posts")
public Page<Post> getPosts(Pageable pageable) {
    return postRepository.findAll(pageable);
}
```
     
### HATEOAS
- HATEOAS 를 사용하려면 먼저 의존성을 추가해주어야한다.
- spring boot에서 관리하는 의존성은 version을 명시해 주지않아도된다.
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```
- Page를 PagedResource로 변경하기.
    - 핸들러 매개변수로 PagedResourceAssenbler 선언
    - return type을 PagedResource<Resource<Posts>> 로 선언.
    - PagedResourceAssembler가 Page<Post> 를 리소스로 변경해주는 역할을 한다.
    
- Page와 관련된 하이퍼미디어 정보를 포함하여 전달해준다.
```
@GetMapping("/posts")
public PagedResources<Resource<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
    return assembler.toResource(postRepository.findAll(pageable) );
}
```    

### Spring data jpa Repository
- @EnableJpaRepositories (entityManager , transactionManager 등을 설정가능)
    - Spring boot 를 사용할때는 자동설정된다.
    - 부트를 사용하지않을 경우엔 @Configuration 클래스에 사용

- @SpringBootApplication이 메타애노테이션으로 되어있기때문에
- Best Practice는 베이스 패키지에 Application class를 두는것이 좋다.

- @Repository를 붙어야할까 ?
    - JpaRepository의 구현체인 SimpleJpaRepopsitory가 이미 @Repository를 가지고있다.
    - 그말은 즉슨 중복.
    - 하지만 붙인다고해서 큰일이 나는건아니다.
     
- 스프링 @Repository
    - SQLException , JpaException을 DataAccessException으로 변환을 해준다.
     
### Spring data jpa Entity save
- JpaRepository의 save는 단순히 entity를 추가하는것이 아니다.

- persist
    - Transient상태의 객체를 persistent상태로 만들어준다.
    - Transient상태란 ?
        - 새로이 생성된 객체
        - Database, PersistentContext모두 모르는상태의 객체.
        
    - Persistent상태란 ?
        - PersistentContext가 관리를 하는상태.
        - 캐싱을 하는상태.
        - 1차캐시 , DirtyChecking , WriteBehind..
- merge
    - detached상태의 객체를 persitent상태로 만들어준다.
    - merge는 상황에 따라 insert , update 쿼리가 발생한다.
    - Detached상태란 ?
        - 1번이라도 Persistent 상태가 된 객체
        - 데이터베이스에 기록이 되어있는객체
        
- 기본전략
    - 어떤 Entity에 id값이 존재하지않는다 ?
    - persist 호출 
    - 해당 인스턴스 자체가 영속이됨.
    - 항상 영속화 되어있는 객체를 리턴해준다.
    
    - 어떤 Entity에 id값이 존재한다 ?
    - merge 호출   

- BestPractice는 리턴받은 인스턴스를 사용할것.
```
@Test
public void save() {
    // transient 상태
    Customer customer = Customer.builder()
//                                    .id(1L)
                                .username("ces518")
                                .password("pjy3859").build(); // persist 호출


    Customer savedCustomer = customers.save(customer);// insert Query 발생

    // persist를 호출하면 persist() 메서드의 인자로받은 객체를 PersistentContext에 영속화한다.
    // 즉 인자로받은 객체와 리턴한 객체는 같다.
    assertThat(entityManager.contains(customer)).isTrue();
    assertThat(entityManager.contains(savedCustomer)).isTrue();
    assertThat(customer == savedCustomer);

    Customer customer1 = Customer.builder()
                                .id(customer.getId())
                                .username("ces5182")
                                .password("pjy3852").build(); // merge 호출

    Customer savedCustomer2 = customers.save(customer1);// update Query 발생

    // merge를 호출하면 merge() 메서드의 인자로 받은 객체의 복사본을 만들고,
    // 해당복사본을 PersistentContext에 영속화 한뒤
    // 해당 복사본을 리턴해준다.
    // 인자로받은 객체와 리턴한 객체는 다르다.
    assertThat(entityManager.contains(savedCustomer2)).isTrue();
    assertThat(entityManager.contains(customer1)).isFalse();
    assertThat(customer1 != savedCustomer2);
}
```


### Spring data jpa Query Method 만들기
- 두가지 방법이 존재한다.
    - method명을 보고 쿼리를 생성하는방법
    - @Query 애노테이션을 활용하여 직접 쿼리를 생성하는방법

- Spring data jpa는 NamedQuery방식도 지원한다.
```java
@Entity
@NamedQuery(name="Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password like ?1")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Builder
    public Customer(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByUsernameStartsWith(String username);

    //Customer.findByPassword
    //method명을 키로사용하여 NamedQuery를 찾는다.
    //NamedQuery를 사용하면 Domain Class가 지저분해짐.
    @Query("SELECT c FROM Customer c WHERE c.password like :password")
    List<Customer> findByPassword(String password);
}
```

### Spring data jpa QueryMethod Sort
- Pageable , Sort 를 매개변수로 사용 할 수있다.
- @Query 애노테이션과 함께 사용할때 제약사항이 존재한다.
- OrderBy 절에서 함수를 호출하는 경우에는 사용할 수 없다.
- JpaSort.unsafe(); 메서드를 이용하면 사용할 수 있다.
- ex) JpaSort.unsafe("LENGTH(title)");
```java
@Query("SELECT c FROM Customer c WHERE c.password like :password")
List<Customer> findByPassword(String password, Sort sort);
```

### Spring data jpa NamedParameter 와 SpEL
- @Query를 사용한 메서드에 @Param(name) 을 사용하면 
- ?1 , ?2 와 같이 채번으로 매핑하는것이아니라 , name으로 참조할 수 있다.
- SpEL지원.
```java
//NamedParameter를 사용하면 해당 파라미터의 이름과 매핑을 해주며,
//변수명이 꼭 파라미터명과 같지않아도 동작한다.
//SpEL 지원 SpringExpressionLanguage를 지원한다.
//EntityNamed변수를 기본적으로 제공하며 해당 엔티티클래스에서 엔티티명이 변경되어도
//@Query 애노테이션을 사용한 곳에서 수정해주지않아도 된다는 장점이 있다.
@Query("SELECT c FROM #{#entityName} c WHERE c.password like :password")
List<Customer> findByPassword2(@Param("password") String password);
```

### Spring data jpa Update Method
- Update 또는 Delete 쿼리 직접 정의하기
- @Query , @Modifying ...
- 추천하는 방법은 아니다.
- Update Query는 PersistenceContext가 관리하는 대상이면 상태를 감지하여 Update쿼리가 발생됨...
- Hibernate PersistenceContext상태에 있기때문에 Update후 바로 select하여 사용하면 데이터가 싱크가 맞지않을 수 있다.
```java
//UPDATE Query를 직접 정의하여 사용할경우 데이터 싱크가 맞지않을 수 있다.
//Update후 바로 select하여 사용할 경우 PersistenceContext에 캐싱된 객체를 사용하기때문.
//Spring에서도 이 문제를 알고 , persistenceContext를 clear해주는 옵션을 제공한다.
@Modifying(clearAutomatically = true)
@Query("UPDATE Customer c SET c.password = ?1 WHERE c.id = ?2")
int updateCustomer(String password, Long id);
```

### Spring data jpa EntityGraph
- fetch모드를 좀더 유연하게 설정 할수있는 기능을 제공한다.
```java
/**
 * NamedEntityGraph 에 그룹명을 지정해주고,
 * attributeNodes에 연관관계를 설정해놓은 이름을 지정해준다.
 *
 * 기본값 : FETCH : 설정해놓은 연관관계는 EAGER로 가져오고 ,나머지는 LAZY로 가져온다.
 *          LOAD  : 설정한 연관관계는 EAGER로 가져오고 , 나머지는 기본전략을 따른다.
 *
 * attributeNodes에 설정해 두지않았더라도 기본형 데이터들은 EAGER로 가져온다.
 */
@NamedEntityGraph(name = "Order.customer",
            attributeNodes = @NamedAttributeNode("customer"))
@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * x2one = fetch default EAGER
     * x2many = fetch default LAZY
     */
    @ManyToOne
    private Customer customer;
}

public interface OrderRepository extends JpaRepository<Order,Long> {

    // attributePaths에 설정하는것이 깔끔한방법.
    // 해당부분이 중복되는경우 Entity상단에 정의해놓고 재사용하는것을 추천한다.
    //@EntityGraph(value = "Order.customer")
    @EntityGraph(attributePaths = "customer")
    Optional<Order> getById(Long id);
}
```
### Spring data jpa Projection
- projection 이란 ?
    - 어떤 Entity의 일부분만 select할 수 있는 기능.

- 인터페이스 기반과 , 클래스기반 
- Closed Projection
    - interface 또는 class에 정의해둔 애트리뷰트에 대해서만 Query를 수행한다.
    - 성능 최적화에 유용하다.
- Open Projection
    - Closed Projection 과 마찬가지로 interface 또는 class에 정의해준 애트리뷰트에 대해 Query를 수행하는데
    - 해당 엔티티의 애트리뷰트를 둘이상 조합하여 새로운 데이터 를 만들경우 해당 엔티티의 모든 애트리뷰트르 조회한 후 실행하게된다.
    - 성능 최적화보다는 특정 데이터를 가공하여 사용할때 용이하다.
```java
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
    @Value("${target.up + ' ' + target.down}")
    String getVotes();
}

/*
  closed 방식
  Interface 기반 Projection 사용
 */
//List<Customer> findByUsername(String username);
List<CustomerSummary> findByUsername(String username);

/*
    Generic을 활용하여 다양한 Projection 사용시 쿼리메서드 하나로 사용 할 수 있다.
 */
<T> List<T> findByUsername(String username, Class<T> type);
```

