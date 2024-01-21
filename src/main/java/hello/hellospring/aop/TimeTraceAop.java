package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

//@Component //스프링 빈 등록. 컴포넌트 스캔으로 등록도 하지만 직접 설정에서 입력하는 것을 권장. 특별한 것이고 보는 사람도 특별한 것임을 알아야 하기 때문.
@Aspect //AOP라는 뜻.
public class TimeTraceAop {
    //실제 메서드 전에 인터셉트 해서 timeTrace 적용한 후 실제 메서드 호출.
    //프록시를 이용해서 가짜 메서드 생성. 호출 측은 가짜 메서드를 호출하고, AOP로직을 거쳐 진짜를 호출한다.
    //joinPoint는 연결되는 지점. AOP를 이용하면 진짜 호출하기 전에 처리를 하거나 조건시 호출을 멈추거나 할 수 있음.
    //hello.hellospring 하위의 모든 패키지에 적용. [execution(패키지명(파라미터))] hello.hellospring.service..*로 하면 service 하위만 적용.
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    // && !target(hello.hellospring.SpringConfig) SpringConfig도 AOP에 포함되었기 때문에 순환참조 발생. SpringConfig은 적용하지 않는 것으로 해결
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString()); //메서드 명 출력

        try {
            return joinPoint.proceed(); //실제 메서드 진행.
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
