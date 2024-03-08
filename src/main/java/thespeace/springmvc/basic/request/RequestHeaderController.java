package thespeace.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * <h1>HTTP 요청 - 기본, 헤더 조회</h1>
 * 애노테이션 기반의 스프링 컨트롤러는 다양한 파라미터를 지원한다.
 * HTTP 헤더 정보를 조회하는 방법을 살펴보자.
 * <p><p>
 *
 * <h2>@Slf4j</h2>
 * 다음 코드를 자동으로 생성해서 로그를 선언해준다. 개발자는 편리하게 {@code log} 라고 사용하면 된다.
 * <blockquote><pre>
 *     private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestHeaderController.class);
 * </pre></blockquote>
 *
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annarguments">공식 메뉴얼(@Controller 의 사용 가능한 파라미터 목록)</a> ,
 *      <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annreturn-types">공식 메뉴얼(@Controller 의 사용 가능한 응답 값 목록)</a>
 */
@Slf4j
@RestController
public class RequestHeaderController {

    /**
     * <h2>{@code MultiValueMap}</h2>
     * <ul>
     *     <li>MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.</li>
     *     <li>HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다
     *          <ul>
     *              <li>request parameter : {@code keyA=value1&keyA=value2}</li>
     *              <li><blockquote><pre>
     * MultiValueMap<String, String> map = new LinkedMultiValueMap();
     * map.add("keyA", "value1");
     * map.add("keyA", "value2");
     *
     * List<String> values = map.get("keyA"); //[value1,value2]
     *              </pre></blockquote></li>
     *          </ul>
     *     </li>
     * </ul>
     *
     * @param request
     * @param response
     * @param httpMethod HTTP 메서드를 조회한다. {@link HttpMethod}
     * @param locale Locale 정보를 조회한다. 다른 방식으로 확장 가능.
     * @param headerMap 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
     * @param host 특정 HTTP 헤더를 조회한다. <br> 속성 : 1. 필수 값 여부: {@code required}, 2. 기본 값 속성: {@code defaultValue}
     * @param cookie 특정 쿠키를 조회한다. <br> 속성 : 1. 필수 값 여부: {@code required}, 2. 기본 값: {@code defaultValue}
     *
     * @see <a href="http://localhost:8080/headers">test url</a>
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}