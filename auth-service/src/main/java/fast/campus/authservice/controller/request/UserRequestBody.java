package fast.campus.authservice.controller.request;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class UserRequestBody {
    private final String userId;

    private final String password;

    @ConstructorProperties({"userId", "password"})
    public UserRequestBody(String password, String userId) {
        this.userId = userId;
        this.password = password;
    }
}
