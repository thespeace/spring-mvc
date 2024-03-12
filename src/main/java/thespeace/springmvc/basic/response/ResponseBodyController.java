package thespeace.springmvc.basic.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import thespeace.springmvc.HelloData;

import java.io.IOException;

/**
 * <h1>HTTP 응답 - HTTP API,메시지 바디에 직접 입력</h1>
 * <p>HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로
 * 데이터를 실어 보내는 응답 정리(정적 리소스나 뷰 템플릿을 거치지 않고, 직접 HTTP 응답 메시지를 전달하는 경우)
 *
 * </p><br>
 * <h2>@RestController</h2>
 * {@code @Controller} + {@code @ResponseBody}<br>
 * 따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력한다. 이름
 * 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.
 * @see <a href="https://web.postman.co/">post man</a>
 */
@Slf4j
//@Controller
//@ResponseBody
@RestController
public class ResponseBodyController {

    /**
     * <h2>HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달</h2>
     * @see <a href="http://localhost:8080/response-body-string-v1">test url</a>
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * <h2>HttpEntity, ResponseEntity(Http Status 추가)</h2>
     * ResponseEntity 엔티티는 HttpEntity를 상속 받았는데, HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고
     * 있다. ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
     * @see <a href="http://localhost:8080/response-body-string-v2">test url</a>
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * <h2>@ResponseBody 사용</h2>
     * {@code @ResponseBody}를 사용하면 view를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할
     * 수 있다. ResponseEntity 도 동일한 방식으로 동작한다
     * @see <a href="http://localhost:8080/response-body-string-v3">test url</a>
     */
//    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /**
     * <h2>ResponseEntity 를 반환</h2>
     * ResponseEntity를 반환한다. HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환된다.
     * @see <a href="http://localhost:8080/response-body-json-v1">test url</a>
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * <h2>@ResponseStatus(HttpStatus.OK)</h2>
     * ResponseEntity 는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody 를 사용하면 이런 것을 설정하기 까다롭다.<br>
     * {@code @ResponseStatus(HttpStatus.OK)} 애노테이션을 사용하면 응답 코드도 설정할 수 있다<br><br>
     *
     * 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다. 프로그램 조건에 따라서 동적으로 변경하려면
     * ResponseEntity 를 사용하면 된다.
     * @see <a href="http://localhost:8080/response-body-json-v2">test url</a>
     */
    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }

}