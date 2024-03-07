package thespeace.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * <h2>기본 요청</h2>
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     * @see <a href="http://localhost:8080/hello-basic">test url</a>
     */
    @RequestMapping(value = {"/hello-basic","/hello-basic/"})
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * <h2>method, 특정 HTTP 메서드 요청만 허용</h2>
     * GET, HEAD, POST, PUT, PATCH, DELETE
     * @see <a href="http://localhost:8080/mapping-get-v1">test url</a>
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * <h2>편리한 축약 애노테이션 (코드보기)</h2>
     * {@link GetMapping}
     * {@link PostMapping}
     * {@link PutMapping}
     * {@link DeleteMapping}
     * {@link PatchMapping}
     * @see <a href="http://localhost:8080/mapping-get-v2">test url</a>
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * <h2>PathVariable(경로변수) 사용</h2>
     * <p>
     * URL경로에 어떤 값을 템플릿 형식으로 쓸 수 있고, PathVariable로 꺼내서 사용할 수 있다.
     * 최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.
     * <ul>
     *     <li>/mapping/userA</li>
     *     <li>/users/1</li>
     *     <p>
     *     <li>@RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 을 사용하면 매칭 되는 부분을
     *         편리하게 조회할 수 있다</li>
     *     <li>@PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.</li>
     *     <li>최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.</li>
     * </ul>
     * </p>
     * {@code @PathVariable("userId") String userId -> @PathVariable String userId}
     *
     * @see <a href="http://localhost:8080/mapping/userA">test url</a>
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data, @PathVariable String userId) {
        log.info("mappingPath userId={}", data);
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    /**
     * <h2>PathVariable 다중 사용</h2>
     * @see <a href="http://localhost:8080/mapping/users/userA/orders/100">test url</a>
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * <h2>특정 파라미터 조건 매핑</h2>
     * 특정 파라미터가 있거나 없는 조건을 추가할 수 있다. 잘 사용하지는 않는다.
     * <ul>
     *     <li>params="mode",</li>
     *     <li>params="!mode"</li>
     *     <li>params="mode=debug"</li>
     *     <li>params="mode!=debug" (! = )</li>
     *     <li>params = {"mode=debug","data=good"}</li>
     * </ul>
     *
     *
     * @see <a href="http://localhost:8080/mapping-param?mode=debug">test url</a>
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * <h2>특정 헤더 조건 매핑</h2>
     * 파라미터 매핑과 비슷하지만, HTTP 헤더를 사용한다. Postman으로 테스트 진행.
     * <ul>
     *     <li>headers="mode",</li>
     *     <li>headers="!mode"</li>
     *     <li>headers="mode=debug"</li>
     *     <li>headers="mode!=debug" (! = )</li>
     * </ul>
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * <h2>미디어 타입, 헤더 기반 조건 매핑 - HTTP 요청 Content-Type(consume)</h2>
     * HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
     * <ul>
     *     <li>consumes="application/json"</li>
     *     <li>consumes="!application/json"</li>
     *     <li>consumes="application/*"</li>
     *     <li>consumes="*\/*"</li>
     *     <li>MediaType.APPLICATION_JSON_VALUE</li>
     * </ul>
     * 예시 : {@code consumes = "text/plain" || consumes = {"text/plain", "application/*"} || consumes = MediaType.TEXT_PLAIN_VALUE}
     *
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * <h2>미디어 타입, 헤더 기반 조건 매핑 - HTTP 요청 Accept(produce)</h2>
     * HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
     * <ul>
     *     <li>produces = "text/html"</li>
     *     <li>produces = "!text/html"</li>
     *     <li>produces = "text/*"</li>
     *     <li>produces = "*\/*"</li>
     * </ul>
     * 예시 : {@code produces = "text/plain" || produces = {"text/plain", "application/*"} || produces = MediaType.TEXT_PLAIN_VALUE || produces = "text/plain;charset=UTF-8"}
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
