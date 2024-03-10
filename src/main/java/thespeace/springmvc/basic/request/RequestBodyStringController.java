package thespeace.springmvc.basic.request;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * <h2>HTTP 요청 메시지 - 단순 텍스트</h2>
 * <ul>HTTP message body에 데이터를 직접 담아서 요청
 *     <ul>
 *         <li>HTTP API에서 주로 사용, JSON, XML, TEXT</li>
 *         <li>데이터 형식은 주로 JSON 사용</li>
 *         <li>POST, PUT, PATCH</li>
 *     </ul>
 * </ul>
 * 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는
 * {@code @RequestParam} , {@code @ModelAttribute}를 사용할 수 없다.
 * (물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * <h2>텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.</h2>
     * <ul>Postman을 사용해서 테스트.
     *     <li><a href="http://localhost:8080/request-body-string-v1">test url</a></li>
     *     <li>Body > row > Text 선택</li>
     * </ul>
     */
    @PostMapping("request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //HTTP 메시지 바디의 데이터를 {@code InputStream}을 사용해서 직접 읽을 수 있다.
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * <h2>Input, Output 스트림, Reader</h2>
     * <ul>
     *     <li>InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회</li>
     *     <li>OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력</li>
     * </ul>
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * <h2>HttpEntity: HTTP header, body 정보를 편리하게 조회</h2>
     * <ul>
     *     <li>메시지 바디 정보를 직접 조회(요청 파라미터를 조회하는 기능과 관계 없음,
     *         {@code @RequestParam} X, {@code @ModelAttribute} X)</li>
     *     <li>헤더 정보 포함 가능</li>
     *     <li>HttpMessageConverter 사용 -> StringHttpMessageConverter 적용</li>
     * </ul>
     * <br>
     * 응답에서도 HttpEntity 사용 가능
     * <ul>
     *     <li>메시지 바디 정보 직접 반환(view 조회X)</li>
     *     <li>헤더 정보 포함 가능</li>
     *     <li>HttpMessageConverter 사용 -> StringHttpMessageConverter 적용</li>
     * </ul>
     * <br>
     * HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다
     * <ul>{@link org.springframework.http.RequestEntity}
     *      <li>HttpMethod, url 정보가 추가, 요청에서 사용</li>
     * </ul>
     * <ul>{@link org.springframework.http.ResponseEntity}
     *      <li>HTTP 상태 코드 설정 가능, 응답에서 사용</li>
     *      <li>{@code return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)}</li>
     * </ul>
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * <h2>@RequestBody, @ResponseBody</h2>
     * <ul>@RequestBody
     *     <li>HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면
     *         HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.</li>
     *     <li>이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam , @ModelAttribute 와는
     *         전혀 관계가 없다.</li>
     * </ul>
     *
     * <ul>@ResponseBody
     *     <li>@ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.</li>
     *     <li>물론 이 경우에도 view를 사용하지 않는다.</li>
     * </ul>
     *
     * <ul>요청 파라미터 vs HTTP 메시지 바디
     *     <li>요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute</li>
     *     <li>HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody</li>
     * </ul>
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody={}", messageBody);

        return "ok";
    }

}