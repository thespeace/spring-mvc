package thespeace.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>요청 매핑 - API 예시</h1>
 * 회원 관리를 HTTP API 매핑 예시(실제 데이터가 넘어가는 부분은 생략, URL 매핑만)
 *
 * @see <a href="https://web.postman.co/">post man</a>
 */
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    /**
     * <h2>회원 목록 조회 : GET /mapping/users</h2>
     */
    @GetMapping
    public String users() {
        return "get users";
    }

    /**
     * <h2>회원 등록 : POST /mapping/users</h2>
     */
    @PostMapping
    public String addUser() {
        return "post user";
    }

    /**
     * <h2>회원 조회 : GET /mapping/users/{userId}</h2>
     */
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    /**
     * <h2>회원 수정 : PATCH /mapping/users/{userId}</h2>
     */
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    /**
     * <h2>회원 삭제 : DELETE /mapping/users/{userId}</h2>
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }
}
