package hello.hellospring.domain.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository<T, Id> T: 타입. 매핑될 엔티티. Id: 엔티티 구분자. id가 Long이니 Long사용.
// 다중 상속을 통해 JpaRepository로 data-jpa 사용할 밑바탕 깔아두고, 생성해야할 MemberRepository도 상속.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //data-jpa가 jpql을 만들어서 주입함. 기본적인 공통쿼리는 이미 만들어져 있고, 비공통만 인터페이스에서 정의.
    //단, 이름 양식을 맞춰야 함. 양식에 맞춰 인터페이스를 짜면 구현체를 생성함.
    Optional<Member> findByName(String name);
}
