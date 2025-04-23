package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

// 다른곳에서 참조해서 사용해야 하기 때문에, private이 아니라 public으로
public class Pointcuts {

  // hello.aop.order 패키지와 하위 패키지
  @Pointcut("execution(* hello.aop.order..*(..))") // pointcut expression
  public void allOrder() {} // pointcut signature

  // 클래스 이름 패턴이 *Service
  @Pointcut("execution(* *..*Service.*(..))")
  public void allService() {}

  // allOrder && allService
  @Pointcut("allOrder() && allService()")
  public void orderAndService() {}
}
