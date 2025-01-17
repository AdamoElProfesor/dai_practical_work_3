package ch.heigvd.dai;


import ch.heigvd.dai.auth.AuthController;
import ch.heigvd.dai.cars.CarsController;
import io.javalin.Javalin;
import ch.heigvd.dai.users.*;
import ch.heigvd.dai.cars.*;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app =
                Javalin.create(config -> config.validation.register(LocalDateTime.class, LocalDateTime::parse));

        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Car> cars = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, LocalDateTime> carsCache = new ConcurrentHashMap<>();
        fillCars(cars, carsCache);



        UsersController usersController = new UsersController(users);
        AuthController authController = new AuthController(users);
        CarsController carsController = new CarsController(cars, users, carsCache);


        app.post("/users", usersController::create);
        app.delete("users/{id}", usersController::delete);

        app.post("/login", authController::login);
        app.post("/logout", authController::logout);

        app.get("/cars", carsController::getAll);
        app.get("/cars/{id}", carsController::getOne);
        app.put("cars/{id}/return", carsController::returnCar);
        app.put("/cars/{id}/rent", carsController::rentCar);

        app.start(PORT);
    }

    public static void fillCars(ConcurrentHashMap<Integer, Car> cars, ConcurrentHashMap<Integer, LocalDateTime> carsCache){
        cars.put(1, new Car() {{
            id = 1;
            brand = "Toyota";
            model = "Corolla";
            engine = "Hybrid";
            power = 120;
            userRenting = null;
        }});
        carsCache.put(1, LocalDateTime.now());

        cars.put(2, new Car() {{
            id = 2;
            brand = "Honda";
            model = "Civic";
            engine = "Gasoline";
            power = 158;
            userRenting = null;
        }});

        carsCache.put(2, LocalDateTime.now());


        cars.put(3, new Car() {{
            id = 3;
            brand = "Ford";
            model = "Focus";
            engine = "Diesel";
            power = 150;
            userRenting = null;
        }});

        carsCache.put(3, LocalDateTime.now());


        cars.put(4, new Car() {{
            id = 4;
            brand = "BMW";
            model = "3 Series";
            engine = "Gasoline";
            power = 255;
            userRenting = null;
        }});

        carsCache.put(4, LocalDateTime.now());


        cars.put(5, new Car() {{
            id = 5;
            brand = "Mercedes-Benz";
            model = "C-Class";
            engine = "Gasoline";
            power = 241;
            userRenting = null;
        }});

        carsCache.put(5, LocalDateTime.now());


        cars.put(6, new Car() {{
            id = 6;
            brand = "Volkswagen";
            model = "Golf";
            engine = "Diesel";
            power = 147;
            userRenting = null;
        }});

        carsCache.put(6, LocalDateTime.now());


        cars.put(7, new Car() {{
            id = 7;
            brand = "Audi";
            model = "A4";
            engine = "Gasoline";
            power = 201;
            userRenting = null;
        }});

        carsCache.put(7, LocalDateTime.now());


        cars.put(8, new Car() {{
            id = 8;
            brand = "Hyundai";
            model = "Elantra";
            engine = "Gasoline";
            power = 147;
            userRenting = null;
        }});

        carsCache.put(8, LocalDateTime.now());


        cars.put(9, new Car() {{
            id = 9;
            brand = "Tesla";
            model = "Model 3";
            engine = "Electric";
            power = 283;
            userRenting = null;
        }});

        carsCache.put(9, LocalDateTime.now());


        cars.put(10, new Car() {{
            id = 10;
            brand = "Chevrolet";
            model = "Malibu";
            engine = "Gasoline";
            power = 160;
            userRenting = null;
        }});

        carsCache.put(10, LocalDateTime.now());

    }
}