package fast.campus.authservice.controller;

import fast.campus.authservice.controller.request.EncryptedUserRequestBody;
import fast.campus.authservice.domain.User;
import fast.campus.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public User CreateNewUser(@RequestBody EncryptedUserRequestBody encryptedUserRequestBody) {
        String userId = encryptedUserRequestBody.getUserId();
        String encryptedPassword = encryptedUserRequestBody.getPassword();

        return userService.createNewUser(userId, encryptedPassword);
    }
}
