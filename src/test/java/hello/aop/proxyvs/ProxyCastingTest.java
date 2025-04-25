package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {


  @Test
  void jdkProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    // error 발생 ==> 구체 클래스로 타입 캐스팅 불가.
    // class jdk.proxy3.$Proxy12 cannot be cast to class hello.aop.member.MemberServiceImpl
    // MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    Assertions.assertThrows(
        ClassCastException.class, () -> {
          MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
  }

  @Test
  void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); // CGLIB 동적 프록시

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
    MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
  }
}
