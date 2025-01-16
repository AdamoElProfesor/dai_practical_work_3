package ch.heigvd.dai.cars;

import ch.heigvd.dai.users.User;
import io.javalin.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CarsController {
    private final ConcurrentHashMap<Integer, Car> cars;
    private final ConcurrentHashMap<Integer, User> users;

    public CarsController(ConcurrentHashMap<Integer, Car> cars , ConcurrentHashMap<Integer, User> users) {
        this.cars = cars;
        this.users = users;
    }

    public void getAll(Context ctx) {
        List<Car> carsList = new ArrayList<>();

        for (Car car : this.cars.values()) {
            carsList.add(car);
        }

        ctx.status(HttpStatus.OK); // 200 OK
        ctx.json(cars);
    }


    public void getOne(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();

        Car car = cars.get(id);

        if (car == null) {
            throw new NotFoundResponse(); // 404 Not Found
        }
        ctx.json(car);
        ctx.status(HttpStatus.OK); // 200 OK
    }

    public void returnCar(Context ctx) {
        String userIdCookie = ctx.cookie("user");
        Integer carId = ctx.pathParamAsClass("id", Integer.class).get();

        if(userIdCookie == null){
            throw new UnauthorizedResponse(); // 401 Unauthorized
        }

        Integer userId = Integer.parseInt(userIdCookie);
        User user = users.get(userId);
        if(user == null){
            throw new UnauthorizedResponse(); // 401 Unauthorized
        }

        Car car = cars.get(carId);
        if (car == null) {
            throw new NotFoundResponse(); // 404 Not Found
        }


        if (!Objects.equals(car.userRenting, userId)) {
            throw new ForbiddenResponse(); // 403 Forbidden
        }

        car.userRenting = null;
        cars.put(carId, car);

        ctx.json(new CarResponse(car.id,
                "Car successfully returned",
                null,
                car.brand,
                car.model)
        );

        ctx.status(HttpStatus.OK); // 200 OK
    }

    public void rentCar(Context ctx) {
        String userIdCookie = ctx.cookie("user");
        Integer carId = ctx.pathParamAsClass("id", Integer.class).get();

        if(userIdCookie == null){
            throw new UnauthorizedResponse(); // 401 Unauthorized
        }

        Integer userId = Integer.parseInt(userIdCookie);
        User user = users.get(userId);
        if(user == null){
            throw new UnauthorizedResponse(); // 401 Unauthorized
        }

        Car car = cars.get(carId);
        if (car == null) {
            throw new NotFoundResponse(); // 404 Not Found
        }

        if (car.userRenting != null) {
            throw new ConflictResponse(); // 409 Conflict
        }

        car.userRenting = userId;
        cars.put(carId, car);

        ctx.json(new CarResponse(car.id,
                "Car successfully rented",
                userId,
                car.brand,
                car.model)
        );
        ctx.status(HttpStatus.OK); // 200 OK
    }

    public static class CarResponse {
        public Integer id;
        public String message;
        public Integer userRenting;
        public String brand;
        public String model;

        public CarResponse(Integer carId, String message, Integer userRenting, String brand, String model) {
            this.id = carId;
            this.message = message;
            this.userRenting = userRenting;
            this.brand = brand;
            this.model = model;
        }
    }

}
