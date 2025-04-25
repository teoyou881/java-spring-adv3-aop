package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Lazy 어노테이션을 사용해서 지연(LAZY) 조회
 */
@Slf4j
@Component
public class CallServiceV2_1 {

  private CallServiceV2 callServiceV2;

  @Autowired
  public void setCallServiceV2(@Lazy CallServiceV2 callServiceV2) {
    this.callServiceV2 = callServiceV2;
  }

  public void external() {
    log.info("call external");
    callServiceV2.internal(); // 외부 메서드 호출
  }

  public void internal() {
    log.info("call internal");
  }
}