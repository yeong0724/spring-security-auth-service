package fast.campus.authservice.controller.request;

import fast.campus.authservice.annotation.PasswordEncryption;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class EncryptedUserRequestBody {
    private final String userId;

    @PasswordEncryption
    private final String password;

    @ConstructorProperties({"userId", "password"})
    public EncryptedUserRequestBody(String password, String userId) {
        this.userId = userId;
        this.password = password;
    }
}
