package hello.aop.pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 이 테스트는 @args 포인트컷을 사용하는 방법을 보여줍니다.
 * @args는 메서드 실행 시점에 전달되는 인수의 타입에 적용된 어노테이션을 기반으로 조인 포인트를 선택합니다.
 */
@Slf4j
public class AtArgsFinalTest {

  @Test
  void atArgsTest() {
    // 스프링 컨텍스트 생성
    AnnotationConfigApplicationContext context = 
        new AnnotationConfigApplicationContext(TestConfig.class);
    
    // 서비스 빈 가져오기
    TestService testService = context.getBean(TestService.class);
    
    // 어노테이션이 없는 인수로 메서드 호출 - 어드바이스 적용 안됨
    log.info("=== 어노테이션이 없는 인수로 호출 ===");
    testService.hello("문자열 인수");
    
    // 어노테이션이 있는 인수로 메서드 호출 - 어드바이스 적용됨
    log.info("=== 어노테이션이 있는 인수로 호출 ===");
    TestArgument arg = new TestArgument();
    testService.hello(arg);
    
    context.close();
  }
  
  // 테스트를 위한 스프링 설정
  @Configuration
  @EnableAspectJAutoProxy
  static class TestConfig {
    
    @Bean
    public TestService testService() {
      return new TestService();
    }
    
    @Bean
    public AtArgsAspect atArgsAspect() {
      return new AtArgsAspect();
    }
  }
  
  // 테스트용 서비스 클래스
  static class TestService {
    public String hello(Object obj) {
      System.out.println("hello 메서드가 호출됨: " + obj);
      return "hello";
    }
  }
  
  // 테스트용 커스텀 어노테이션
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestAnnotation {}
  
  // 어노테이션이 적용된 클래스
  @TestAnnotation
  static class TestArgument {
    @Override
    public String toString() {
      return "TestArgument 객체";
    }
  }
  
  // @args를 사용하는 애스펙트
  @Slf4j
  @Aspect
  static class AtArgsAspect {
    
    /**
     * @args 포인트컷은 메서드 실행 시점에 전달된 인수의 타입에 지정된 어노테이션이 있는 경우 매칭됩니다.
     * 이 예제에서는 TestAnnotation이 적용된 타입의 인수가 전달될 때 어드바이스가 적용됩니다.
     */
    @Around("@args(hello.aop.pointcut.AtArgsFinalTest.TestAnnotation)")
    public Object atArgs(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[@args 어드바이스 적용] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }
  }
}