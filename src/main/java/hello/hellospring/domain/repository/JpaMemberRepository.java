package hello.hellospring.domain.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    public final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Member save(Member member) {
        em.persist(member);
        return member; //도메인 객체에 자동으로 반영되나?
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //매핑할 객체를 입력하고 쿼리를 적용. 파라미터로 값 입력. 결과는 스트림으로 받아 처리한다.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //테이블이 아니라 객체가 쿼리의 대상이 됨.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
