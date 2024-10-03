package fast.campus.authservice.controller;

import fast.campus.authservice.controller.request.OtpRequestBody;
import fast.campus.authservice.controller.request.UserRequestBody;
import fast.campus.authservice.service.OtpService;
import fast.campus.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;

    @PostMapping("/user/auth")
    public String auth(@RequestBody UserRequestBody userRequestBody) {
        return userService.auth(userRequestBody.getUserId(), userRequestBody.getPassword());
    }

    @PostMapping("/otp/check")
    public boolean auth(@RequestBody OtpRequestBody otpRequestBody) {
        return otpService.checkOtp(otpRequestBody.getUserId(), otpRequestBody.getOtp());
    }
}
