package hello.hellospring.domain.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);//optional: java8에서 추가. null이 반환될 수 있는데, null반환보다는 optional로 감싸는게 선호된다

    Optional<Member> findByName(String name);

    List<Member> findAll();
}
