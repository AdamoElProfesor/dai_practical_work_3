package ch.heigvd.dai.users;

import io.javalin.http.*;
import io.javalin.validation.ValidationException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersController{
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicInteger userIdCounter = new AtomicInteger(1);

    public UsersController(ConcurrentHashMap<Integer, User> users) {
        this.users = users;
    }

    public void create(Context ctx) {
        try{
            User newUser =
                    ctx.bodyValidator(User.class)
                            .check(obj -> obj.firstName != null, "Missing first name")
                            .check(obj -> obj.lastName != null, "Missing last name")
                            .check(obj -> obj.email != null, "Missing email")
                            .check(obj -> obj.password != null, "Missing password")
                            .get();

            for (User user : users.values()) {
                if (user.email.equalsIgnoreCase(newUser.email)) {
                    throw new ConflictResponse(); // 409 Conflict
                }
            }

            User user = new User();

            user.id = userIdCounter.getAndIncrement();
            user.firstName = newUser.firstName;
            user.lastName = newUser.lastName;
            user.email = newUser.email;
            user.password = newUser.password;

            users.put(user.id, user);

            ctx.status(HttpStatus.CREATED);  // 201 Created
            ctx.json(new UserResponse(user));

        }catch (ValidationException e) {
            throw new BadRequestResponse(); // 400 Bad Request
        }
    }
    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();

        if (!users.containsKey(id)) {
            throw new NotFoundResponse(); // 404 Not found
        }

        users.remove(id);

        ctx.status(HttpStatus.NO_CONTENT); // 204 No Content
    }
    public class UserResponse{
        public Integer id;
        public String firstName;
        public String lastName;

        public UserResponse(User user){
            this.id = user.id;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
        }
    }
}