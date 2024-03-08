package thespeace.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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
     * @see <a href="http://localhost:8080/basic/hello-form.html">HTML form test url</a>
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username"); //request.getParameter() : query parameter, HTML Form data 모두 조회 가능.
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }
}
