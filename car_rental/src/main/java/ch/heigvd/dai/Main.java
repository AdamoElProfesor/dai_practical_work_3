package ch.heigvd.dai;

import ch.heigvd.dai.auth.AuthController;
import io.javalin.Javalin;
import ch.heigvd.dai.users.*;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

        UsersController usersController = new UsersController(users);
        AuthController authController = new AuthController(users);

        app.post("/users", usersController::create);

        app.post("/login", authController::login);

        app.start(PORT);
    }
}