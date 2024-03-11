package thespeace.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import thespeace.springmvc.HelloData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <h2>HTTP 요청 메시지 - JSON</h2>
 * <ul>
 *     <li><a href="https://www.postman.com/">postMan(test url)</a></li>
 *     <li>test json raw : {"username":"hello", "age":20}</li>
 *     <li>content-type : application/json</li>
 * </ul>
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    //문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환.
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * <h2>HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환</h2>
     * @see <a href="http://localhost:8080/request-body-json-v1">test url</a>
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /**
     * <h2>@RequestBody 문자 변환</h2>
     * <ul> {@code @RequestBody}
     *     <li>HTTP 메시지에서 데이터를 꺼내고 messageBody에 저장</li>
     *     <li>문자로 된 JSON 데이터인 messageBody 를 objectMapper 를 통해서 자바 객체로 변환</li>
     *     <li>HttpMessageConverter 사용 -> StringHttpMessageConverter 적용</li>
     * </ul>
     *
     * <ul> {@code @ResponseBody}
     *     <li>모든 메서드에 @ResponseBody 적용</li>
     *     <li>메시지 바디 정보 직접 반환(view 조회X)</li>
     *     <li>HttpMessageConverter 사용 -> StringHttpMessageConverter 적용</li>
     * </ul>
     * @see <a href="http://localhost:8080/request-body-json-v2">test url</a>
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);

        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /**
     * <h2>@RequestBody 객체 변환</h2>
     * <ul>@RequestBody 객체 파라미터
     *     <li>{@code @RequestBody HelloData data}</li>
     *     <li>{@code @RequestBody}에 직접 만든 객체를 지정할 수 있다.</li>
     *     <li>{@code @RequestBody} 생략 불가능({@code @ModelAttribute}가 적용되어 버림)</li>
     *     <li>HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)</li>
     * </ul>
     * HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나
     * 객체 등으로 변환해준다. 뿐만 아니라 JSON도 객체로 변환해준다.
     * <br><br>
     * 참고 : 스프링은 @ModelAttribute , @RequestParam 과 같은 해당 애노테이션을 생략시 다음과 같은 규칙을 적용한다.
     * <ul>
     *     <li>String , int , Integer 같은 단순 타입 = @RequestParam</li>
     *     <li>나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)</li>
     * </ul>
     * @see <a href="http://localhost:8080/request-body-json-v3">test url</a>
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * <h2>HttpEntity</h2>
     * @see <a href="http://localhost:8080/request-body-json-v4">test url</a>
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * <h2>@ResponseBody</h2>
     * 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
     * 물론 이 경우에도 HttpEntity 를 사용해도 된다.
     *
     * <ul>@RequestBody 요청
     *     <li>JSON 요청 -> HTTP 메시지 컨버터 -> 객체</li>
     *     <li>HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
     *     application/json)</li>
     * </ul>
     * <ul>@ResponseBody 응답
     *     <li>객체 -> HTTP 메시지 컨버터 -> JSON 응답</li>
     *     <li>HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept:
     *     application/json)</li>
     * </ul>
     * @see <a href="http://localhost:8080/request-body-json-v5">test url</a>
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }
}

/*


*/
