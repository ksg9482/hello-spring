package hello.hellospring.domain.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();//회워id를 key로 쓸건데 id 타입이 Long. 현업에선 동시성 문제 때문에 그냥 HashMap 말고 다른방법 사용.
    private static long sequence = 0L;//1씩 올린다. 현업에선 동시성 문제 때문에 다른방법 사용.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);//id를 저장하기 전 숫자 1상승
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));//값이 없으면 null이 나올 수 있다. 그래서 optional로 감싸서 후속처리를 할 수 있게 한다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        //람다 이용. findAny는 1개라도 발견되면 바로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
