package thespeace.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * <h2>HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form</h2>
 * 클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법을 사용한다.
 * <ul>
 *     <li>GET - 쿼리 파라미터 <ul>
 *         <li>/url**?username=hello&age=20**</li>
 *         <li>메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달</li>
 *         <li>ex) 검색, 필터, 페이징등에서 많이 사용하는 방식</li>
 *     </ul></li>
 *     <li>POST - HTML Form<ul>
 *         <li>content-type: application/x-www-form-urlencoded</li>
 *         <li>메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20</li>
 *         <li>예) 회원 가입, 상품 주문, HTML Form 사용</li>
 *     </ul></li>
 *     <li>HTTP message body에 데이터를 직접 담아서 요청<ul>
 *         <li>HTTP API에서 주로 사용, JSON, XML, TEXT</li>
 *         <li>데이터 형식은 주로 JSON 사용</li>
 *         <li>POST, PUT, PATCH</li>
 *     </ul></li>
 * </ul>
 */
@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     * @see <a href="http://localhost:8080/request-param-v1?username=hello&age=20">test url</a>
     * @see <a href="http://localhost:8080/basic/hello-form.html">HTML form test url</a>
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username"); //request.getParameter() : query parameter, HTML Form data 모두 조회 가능.
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * @param memberName @RequestParam 사용
     * @param memberAge 파라미터 이름으로 바인딩
     * @see <a href="http://localhost:8080/request-param-v2?username=hello&age=20">test url</a>
     */
    @ResponseBody //View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력 == @RestController와 같은 효과. 클래스단위 -> 메서드단위.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);

        return "ok";
    }

    /**
     * @param username @RequestParam 사용
     * @param age HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     * @see <a href="http://localhost:8080/request-param-v3?username=hello&age=20">test url</a>
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @param username @RequestParam 사용
     * @param age String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
     * @see <a href="http://localhost:8080/request-param-v4?username=hello&age=20">test url</a>
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * <h2>파라미터 필수 여부 - requestParamRequired</h2>
     * @param username 필수(default), 빈문자 입력받을 수 있다. parameter가 없으면 400 예외가 발생
     * @param age int age -> null을 int에 입력하는 것은 불가능(500 예외 발생), 따라서 Integer 변경해야 함(또는 defaultValue 사용)
     *
     * @see <a href="http://localhost:8080/request-param-required?username=hello&age=20">test url</a>
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * <h2>기본 값 적용 - requestParamDefault</h2>
     * @param username defaultValue 사용
     * @param age defaultValue는 빈 문자의 경우에도 적용
     *
     * @see <a href="http://localhost:8080/request-param-default?username=hello&age=20">test url</a>
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * <h2>파라미터를 Map으로 조회하기 - requestParamMap</h2>
     * @param paramMap 파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자.
     * <ul>
     *     <li>Map : Map(key=value)</li>
     *     <li>MultiValueMap : MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])</li>
     * </ul>
     *
     * @see <a href="http://localhost:8080/request-param-map?username=hello&age=20">test url</a>
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }
}
