package ch.heigvd.dai;

import io.javalin.Javalin;
import ch.heigvd.dai.users.*;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

        UsersController usersController = new UsersController(users);

        app.post("/users", usersController::create);
        app.delete("users/{id}", usersController::delete);

        app.start(PORT);
    }
}