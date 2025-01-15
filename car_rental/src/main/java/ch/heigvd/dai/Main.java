package ch.heigvd.dai;


import ch.heigvd.dai.auth.AuthController;
import ch.heigvd.dai.cars.CarsController;
import io.javalin.Javalin;
import ch.heigvd.dai.users.*;
import ch.heigvd.dai.cars.*;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Car> cars = new ConcurrentHashMap<>();
        fillCars(cars);


        UsersController usersController = new UsersController(users);
        AuthController authController = new AuthController(users);
        CarsController carsController = new CarsController(cars);


        app.post("/users", usersController::create);
        app.delete("users/{id}", usersController::delete);

        app.post("/login", authController::login);

        app.get("/cars/{id}", carsController::getOne);

        app.start(PORT);
    }

    public static void fillCars(ConcurrentHashMap<Integer, Car> cars){
        cars.put(1, new Car() {{
            id = 1;
            brand = "Toyota";
            model = "Corolla";
            engine = "Hybrid";
            power = 120;
            userRenting = null;
        }});

        cars.put(2, new Car() {{
            id = 2;
            brand = "Honda";
            model = "Civic";
            engine = "Gasoline";
            power = 158;
            userRenting = null;
        }});

        cars.put(3, new Car() {{
            id = 3;
            brand = "Ford";
            model = "Focus";
            engine = "Diesel";
            power = 150;
            userRenting = null;
        }});

        cars.put(4, new Car() {{
            id = 4;
            brand = "BMW";
            model = "3 Series";
            engine = "Gasoline";
            power = 255;
            userRenting = null;
        }});

        cars.put(5, new Car() {{
            id = 5;
            brand = "Mercedes-Benz";
            model = "C-Class";
            engine = "Gasoline";
            power = 241;
            userRenting = null;
        }});

        cars.put(6, new Car() {{
            id = 6;
            brand = "Volkswagen";
            model = "Golf";
            engine = "Diesel";
            power = 147;
            userRenting = null;
        }});

        cars.put(7, new Car() {{
            id = 7;
            brand = "Audi";
            model = "A4";
            engine = "Gasoline";
            power = 201;
            userRenting = null;
        }});

        cars.put(8, new Car() {{
            id = 8;
            brand = "Hyundai";
            model = "Elantra";
            engine = "Gasoline";
            power = 147;
            userRenting = null;
        }});

        cars.put(9, new Car() {{
            id = 9;
            brand = "Tesla";
            model = "Model 3";
            engine = "Electric";
            power = 283;
            userRenting = null;
        }});

        cars.put(10, new Car() {{
            id = 10;
            brand = "Chevrolet";
            model = "Malibu";
            engine = "Gasoline";
            power = 160;
            userRenting = null;
        }});
    }
}