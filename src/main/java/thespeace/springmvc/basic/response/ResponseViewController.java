package thespeace.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h1>HTTP 응답 - 정적 리소스, 뷰 템플릿</h1>
 * 스프링(서버)에서 응답 데이터를 만드는 방법은 크게 3가지이다
 * <ul>
 *     <li>정적 리소스<ul>
 *         <li>예) 웹 브라우저에 정적인 HTML, css, js를 제공할 때는, 정적 리소스를 사용한다.</li>
 *     </ul></li>
 *     <li>뷰 템플릿 사용<ul>
 *         <li>예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.</li>
 *     </ul></li>
 *     <li>HTTP 메시지 사용<ul>
 *         <li>HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에
 *             JSON 같은 형식으로 데이터를 실어 보낸다.</li>
 *     </ul></li>
 * </ul>
 *
 * <h2>1. 정적 리소스</h2>
 * 스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.
 * {@code /static}, {@code /public}, {@code /resources}, {@code /META-INF/resources}
 * <br><br>
 * {@code src/main/resources}는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로이다.
 * 따라서 다음 디렉토리에 리소스를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.
 * <br><br>
 * 정적 리소스 경로 : {@code src/main/resources/static}
 *
 * <h2>2. 뷰 템플릿</h2>
 * 뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전달한다.
 * 일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수 있는 것이라
 * 면 뭐든지 가능하다.
 * <br><br>
 * 뷰 템플릿 경로 : {@code src/main/resources/templates}
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.4.3/reference/html/appendix-applicationproperties.html#common-application-properties-templating">
 *      공식 사이트(스프링 부트의 타임리프 관련 추가 설정 확인)</a>
 */
@Controller
public class ResponseViewController {

    /**
     * <h2>뷰 템플릿을 호출</h2>
     * @see <a href="http://localhost:8080/response-view-v1">test url</a>
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    /**
     * <h2>뷰 템플릿을 호출(String을 반환하는 경우 - View or HTTP 메시지)</h2>
     * {@code @ResponseBody} 가 없으면 {@code response/hello}로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.<br>
     * {@code @ResponseBody} 가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 {@code response/hello}라는 문자가 입력된다.<br>
     * <ul>여기서는 뷰의 논리 이름인 {@code response/hello}를 반환하면 다음 경로의 뷰 템플릿이 렌더링 되는 것을 확인할 수 있다.
     *     <li>실행: {@code templates/response/hello.html}</li>
     * </ul>
     * @see <a href="http://localhost:8080/response-view-v2">test url</a>
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }

    /**
     * <h2>뷰 템플릿을 호출(Void를 반환하는 경우)</h2>
     * <ul>
     *     <li>{@code @Controller} 를 사용하고, {@code HttpServletResponse}, {@code OutputStream(Writer)}같은 HTTP 메시지 바디를
     *         처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용.
     *              <ul>
     *              <li>요청 URL: {@code /response/hello}</li>
     *              <li>실행: {@code templates/response/hello.html}</li>
     *          </ul>
     *     </li>
     *     <li><b>참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.</b></li>
     * </ul>
     * @see <a href="http://localhost:8080/response/hello">test url</a>
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }

}
