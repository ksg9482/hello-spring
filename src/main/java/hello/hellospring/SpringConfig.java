package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.domain.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration //이거읽고
public class SpringConfig {
    /* //그냥 jpa까지
    private DataSource dataSource;
    private EntityManager em;
    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
    */
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean //스프링빈에 등록
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    @Bean //AOP는 정형화 된 것이 아니기 때문에 보통 인지하기 편하게 설정파일에 작성.
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
}
