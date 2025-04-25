package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDiAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 플록시
// @SpringBootTest
@Import(ProxyDiAspect.class)
public class ProxyDiTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberServiceImpl memberServiceImpl;

  @Test
  void go() {
    log.info("memberService={}", memberService);
    log.info("memberServiceImpl={}", memberServiceImpl);

    memberServiceImpl.hello("hello");
  }
}
