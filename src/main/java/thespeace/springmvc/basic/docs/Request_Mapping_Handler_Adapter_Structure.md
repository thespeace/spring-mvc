# 요청 매핑 핸들러 어뎁터 구조
HTTP 메시지 컨버터는 스프링 MVC 어디쯤에서 사용되는 것일까?

### 1. SpringMVC 구조
![SpringMVC 구조](Request_Mapping_Handler_Adapter_Structure1.PNG)

모든 비밀은 애노테이션 기반의 컨트롤러, 그러니까 `@RequestMapping`을 처리하는 핸들러 어댑터인 `RequestMappingHandlerAdapter`(요청 매핑 헨들러 어뎁터)에 있다.

<br>

### 2. RequestMappingHandlerAdapter 동작 방식
![RequestMappingHandlerAdapter 동작 방식](Request_Mapping_Handler_Adapter_Structure2.PNG)

<br>

### 3. ArgumentResolver

생각해보면, 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다.<br>
`HttpServletRequest` , `Model`은 물론이고, `@RequestParam`, `@ModelAttribute`같은 애노테이션 그리고 `@RequestBody`, `HttpEntity` 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다.<br>
이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 `ArgumentResolver`덕분이다.
애노테이션 기반 컨트롤러를 처리하는 `RequestMappingHandlerAdapter`는 바로 이 `ArgumentResolver`를 호출해서 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터의 값(객체)을 생성한다.<br>
그리고 이렇게 파리미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.<br><br>

스프링은 30개가 넘는 `ArgumentResolver`를 기본으로 제공한다.<br>
어떤 종류들이 있는지 살짝 코드로 확인만 해보자.

> 참고 : 가능한 파라미터 목록은 다음 공식 메뉴얼에서 확인할 수 있다. <br>
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annarguments

<br>

정확히는 `HandlerMethodArgumentResolver`인데 줄여서 `ArgumentResolver`라고 부른다.
```java
public interface HandlerMethodArgumentResolver {
    
    boolean supportsParameter(MethodParameter parameter);

    @Nullable
    Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                           NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception;
}
```

<br>

### 4. 동작 방식
`ArgumentResolver` 의 `supportsParameter()` 를 호출해서 해당 파라미터를 지원하는지 체크하고, 지원하면 `resolveArgument()` 를 호출해서 실제 객체를 생성한다. 그리고 이렇게 생성된 객체가 컨트롤러 호출시 넘어가는 것이다.<br>
그리고 원한다면 직접 이 인터페이스를 확장해서 원하는 `ArgumentResolver`를 만들 수도 있다.

<br>

### 5. ReturnValueHandler
`HandlerMethodReturnValueHandler`를 줄여서 `ReturnValueHandler`라 부른다.<br>
`ArgumentResolver`와 비슷한데, 이것은 응답 값을 변환하고 처리한다.<br><br>

컨트롤러에서 `String`으로 뷰 이름을 반환해도, 동작하는 이유가 바로 `ReturnValueHandler`덕분이다.<br>
어떤 종류들이 있는지 살짝 코드로 확인만 해보자.<br><br>

스프링은 10여개가 넘는 ReturnValueHandler 를 지원한다.<br>
예) `ModelAndView`, `@ResponseBody`, `HttpEntity`, `String`...

> 참고 : 가능한 응답 값 목록은 다음 공식 메뉴얼에서 확인할 수 있다. <br>
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annreturn-types

<br>

## 6. HTTP 컨버터
* HTTP 메시지 컨버터 위치<br>
![HTTP 메시지 컨버터 위치](Request_Mapping_Handler_Adapter_Structure3.PNG)
* HTTP 메시지 컨버터를 사용하는 `@RequestBody`도 컨트롤러가 필요로 하는 파라미터의 값에 사용된다.<br>`@ResponseBody`의 경우도 컨트롤러의 반환 값을 이용한다.
* **요청의 경우** `@RequestBody` 를 처리하는 `ArgumentResolver`가 있고, `HttpEntity`를 처리하는 `ArgumentResolver`가 있다.<br>이 `ArgumentResolver` 들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다. (소스코드 확인 -> `HttpEntityMethodProcessor.java`)
* **응답의 경우** `@ResponseBody`와 `HttpEntity`를 처리하는 `ReturnValueHandler`가 있다. 그리고 여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.
* 스프링 MVC는 `@RequestBody`,`@ResponseBody` 가 있으면 `RequestResponseBodyMethodProcessor() HttpEntity` 가 있으면 `HttpEntityMethodProcessor()`를 사용한다.

> 참고 : `HttpMessageConverter.java`를 구현한 클래스를 한번 확인해보자.

<br>

### 7. 확장
스프링은 다음을 모두 인터페이스로 제공한다. 따라서 필요하면 언제든지 기능을 확장할 수 있다.
* `HandlerMethodArgumentResolver`
* `HandlerMethodReturnValueHandler`
* `HttpMessageConverter`

스프링이 필요한 대부분의 기능을 제공하기 때문에 실제 기능을 확장할 일이 많지는 않다. 기능 확장은 `WebMvcConfigurer`를 상속 받아서 스프링 빈으로 등록하면 된다. 실제 자주 사용하지는 않으니 실제 기능 확장이 필요할 때 `WebMvcConfigurer`를 검색해보자.<br><br>

* WebMvcConfigurer 확장
```java
@Bean
public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            
            //ArgumentResolvers 추가
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
                //...
            }
            
            //MessageConverters 추가
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
                //...
            }
        };    
    }
```
