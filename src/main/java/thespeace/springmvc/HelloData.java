package thespeace.springmvc;

import lombok.Data;

/**
 * <h2>요청 파라미터를 바인딩 받을 객체</h2>
 * {@link Data}
 * -@Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 를 자동으로 적용해준다.
 */
@Data
public class HelloData {

    private String username;
    private int age;
}
