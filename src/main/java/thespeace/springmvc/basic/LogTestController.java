package thespeace.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @see <a href="http://localhost:8080/log-test">test url</a>
 * <h1>로깅 간단히 알아보기</h1>
 *
 * <p>운영 시스템에서는 System.out.println() 같은 시스템 콘솔을 사용해서 필요한 정보를 출력하지 않고, 별도의 로
 * 깅 라이브러리를 사용해서 로그를 출력한다.
 *
 * </p>
 * <p><p>
 *
 * <h2>로깅 라이브러리</h2>
 * 스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리( spring-boot-starter-logging )가 함께 포
 * 함된다. 스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.
 * <ul>
 *     <li>SLF4J - http://www.slf4j.org</li>
 *     <li>Logback - http://logback.qos.ch</li>
 * </ul>
 * 로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는
 * 것이 바로 SLF4J 라이브러리다. 쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.
 * 실무에서는 스프링 부트가 기본으로 제공하는 Logback을 대부분 사용한다.
 * <p><p>
 *
 * <h2>매핑 정보</h2>
 * <ul>
 *     <li><code>@RestController<code/>
 *     <ul>
 *         <li><code>@Controller</code>는 반환 값이 <code>String</code>이면 뷰 이름으로 인식된다. 그래서 <b>뷰를 찾고 뷰가 랜더링</b> 된다.</li>
 *         <li><code>@RestController</code>는 반환 값으로 뷰를 찾는 것이 아니라, <b>HTTP 메시지 바디에 바로 입력</b>한다.
 *             따라서 실행결과로 ok 메시지를 받을 수 있다. <code>@ResponseBody</code>와 관련이 있는데 추후에 알아보자.</li>
 *     </ul>
 *     </li>
 * </ul>
 * <p><p>
 *
 * <h2>테스트</h2>
 * <ul>
 *     <li>로그가 출력되는 포멧 확인
 *          <ul>
 *              <li>시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지</li>
 *          </ul>
 *     </li>
 *     <li>로그 레벨 설정을 변경해서 출력 결과를 보자.
 *          <ul>
 *              <li>LEVEL: TRACE > DEBUG > INFO > WARN > ERROR</li>
 *              <li>개발 서버는 debug 출력</li>
 *              <li>운영 서버는 info 출력</li>
 *          </ul>
 *     </li>
 *     <li><code>@Slf4j</code>로 변경</li>
 * </ul>
 * <p><p>
 *
 * <h2>로그 사용시 장점</h2>
 * <ul>
 *     <li>쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.</li>
 *     <li>로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게
 *         조절할 수 있다.</li>
 *     <li>시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파
 *         일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.</li>
 *     <li>성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한
 *         다.</li>
 * </ul>
 *
 * @Learnmore <a href="https://www.slf4j.org/">SLF4J</a>, <a href="https://logback.qos.ch/">Logback</a>, <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging">Spring boot Logging</a>
 */
//@Slf4j
@RestController
public class LogTestController {

    //로그 선언, @Slf4j로 대체 가능(lombok).
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        //로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data 더하기 연산이 실제 실행이 되어 버린다.
        //결과적으로 문자 더하기 연산이 발생한다. 이로써 필요없는 리소스를 사용하게 된다.
        log.trace(" info log=" + name);

        //아래 코드는 위 코드처럼 의미없는 연산이 발생하지 않음으로 "올바른 로그 사용법"이다.
        log.trace("trace log={}", name); //로컬 PC
        log.debug("debug log={}", name); //개발 서버
        log.info(" info  log={}", name); //실제 서비스는 info레벨을 많이 사용.
        log.warn(" warn  log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
