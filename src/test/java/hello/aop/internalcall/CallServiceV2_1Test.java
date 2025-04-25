package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest(classes = {})
class CallServiceV2_1Test {

  @Autowired
  CallServiceV2_1 callServiceV2_1;

  @Test
  void external() {
    callServiceV2_1.external();
  }

  @Test
  void internal() {
    callServiceV2_1.internal();
  }
}
