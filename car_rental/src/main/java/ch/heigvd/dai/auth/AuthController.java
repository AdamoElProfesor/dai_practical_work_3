package ch.heigvd.dai.auth;

import ch.heigvd.dai.users.User;
import io.javalin.http.*;
import io.javalin.validation.ValidationException;

import java.util.concurrent.ConcurrentHashMap;

public class AuthController {
    private final ConcurrentHashMap<Integer, User> users;
    public AuthController(ConcurrentHashMap<Integer, User> users) {
        this.users = users;
    }

    public void login(Context ctx){
        try {
            User loginUser =
                    ctx.bodyValidator(User.class)
                            .check(obj -> obj.email != null, "Missing email")
                            .check(obj -> obj.password != null, "Missing password")
                            .get();

            for (User user : users.values()) {
                if (user.email.equalsIgnoreCase(loginUser.email)
                        && user.password.equals(loginUser.password)) {
                    ctx.cookie("user", String.valueOf(user.id));
                    ctx.status(HttpStatus.NO_CONTENT); // 204 No Content
                    return;
                }
            }

            throw new UnauthorizedResponse(); // 401 Unauthorized
        }catch (ValidationException e){
            throw new BadRequestResponse(); // 400 Bad Request
        }
    }
    public void logout(Context ctx) {
        ctx.removeCookie("user");
        ctx.status(HttpStatus.NO_CONTENT);
    }
}
