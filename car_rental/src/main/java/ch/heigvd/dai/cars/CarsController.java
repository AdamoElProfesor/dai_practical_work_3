package ch.heigvd.dai.cars;

import ch.heigvd.dai.users.User;
import io.javalin.http.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CarsController {
    private final ConcurrentHashMap<Integer, Car> cars;
    private final ConcurrentHashMap<Integer, User> users;

    private final ConcurrentHashMap<Integer, LocalDateTime> carsCache;

    // This is a magic number used to store the cars' list last modification date
    // As the ID for cars starts from 1, it is safe to reserve the value -1 for all cars
    private final Integer RESERVED_ID_TO_IDENTIFY_ALL_CARS = -1;

    public CarsController(ConcurrentHashMap<Integer, Car> cars , ConcurrentHashMap<Integer, User> users, ConcurrentHashMap<Integer, LocalDateTime> carsCache) {
        this.cars = cars;
        this.users = users;
        this.carsCache = carsCache;
    }

    public void getAll(Context ctx) {
        LocalDateTime lastKnownModification =
                ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

        if (lastKnownModification != null &&
                carsCache.containsKey(RESERVED_ID_TO_IDENTIFY_ALL_CARS) &&
                carsCache.get(RESERVED_ID_TO_IDENTIFY_ALL_CARS).equals(lastKnownModification)) {
            throw new NotModifiedResponse(); // 304 Not Modified
        }

        LocalDateTime now;
        if(carsCache.containsKey(RESERVED_ID_TO_IDENTIFY_ALL_CARS)) {
            now = carsCache.get(RESERVED_ID_TO_IDENTIFY_ALL_CARS);
        }else{
            now = LocalDateTime.now();
            carsCache.put(RESERVED_ID_TO_IDENTIFY_ALL_CARS, now);
        }

        ctx.header("Last-Modified", String.valueOf(now));
        ctx.status(HttpStatus.OK); // 200 OK
        ctx.json(cars);
    }


    public void getOne(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        LocalDateTime lastKnownModification = ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

        Car car = cars.get(id);

        if (car == null) {
            throw new NotFoundResponse(); // 404 Not Found
        }

        // Check if the car has been modified since the last known modification date
        if (lastKnownModification != null && carsCache.get(id).equals(lastKnownModification)) {
            throw new NotModifiedResponse(); // 304 Not Modified
        }

        LocalDateTime now;
        if(carsCache.containsKey(id)){
            now = carsCache.get(id);
        }else{
            now = LocalDateTime.now();
            carsCache.put(id, now);
        }

        ctx.header("Last-Modified", String.valueOf(now));
        ctx.json(car);
        ctx.status(HttpStatus.OK); // 200 OK
    }

    public void returnCar(Context ctx) {
        String userIdCookie = ctx.cookie("user");
        Integer carId = ctx.pathParamAsClass("id", Integer.class).get();
        LocalDateTime lastKnownModification =
                ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);


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

        if(lastKnownModification != null && !carsCache.get(carId).equals(lastKnownModification)){
            throw new PreconditionFailedResponse(); // 412 Precondition Failed
        }


        if (!Objects.equals(car.userRenting, userId)) {
            throw new ForbiddenResponse(); // 403 Forbidden
        }

        car.userRenting = null;
        cars.put(carId, car);

        LocalDateTime now;
        if(carsCache.containsKey(carId)){
            now = carsCache.get(carId);
        }else{
            now = LocalDateTime.now();
            carsCache.put(carId, now);

            carsCache.remove(RESERVED_ID_TO_IDENTIFY_ALL_CARS);
        }

        ctx.header("Last-Modified", String.valueOf(now));
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
        LocalDateTime lastKnownModification =
                ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);

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


        if(lastKnownModification != null && !carsCache.get(carId).equals(lastKnownModification)){
            throw new PreconditionFailedResponse(); // 412 Precondition Failed
        }

        if (car.userRenting != null) {
            throw new ConflictResponse(); // 409 Conflict
        }

        car.userRenting = userId;
        cars.put(carId, car);

        LocalDateTime now;
        if(carsCache.containsKey(carId)){
            now = carsCache.get(carId);
        }else{
            now = LocalDateTime.now();
            carsCache.put(carId, now);

            carsCache.remove(RESERVED_ID_TO_IDENTIFY_ALL_CARS);
        }

        ctx.header("Last-Modified", String.valueOf(now));
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
