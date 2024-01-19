package hello.hellospring.service;

import hello.hellospring.domain.Member;
import static  org.assertj.core.api.Assertions.*;

import hello.hellospring.domain.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// org.assertj.core.api
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //매 테스트 마다 서비스와 같은 인스턴스를 공유하도록 함.
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given - 이 기반으로
        Member member = new Member();
        member.setName("hello");

        //when - 이렇게 했을 때
        Long saveId = memberService.join(member);

        //then - 이게 나와야 한다
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given - 이 기반으로
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when - 이렇게 했을 때
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        //방법1
        //try {
        //    //when - 이렇게 했을 때
        //    memberService.join(member2);
        //    fail(); //예외가 발생해야 하는데 더 진행된 것. 테스트 실패
        //} catch (IllegalStateException e) {
        //    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        //}
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}