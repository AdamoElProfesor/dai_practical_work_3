package ch.heigvd.dai.cars;

import ch.heigvd.dai.users.User;
import io.javalin.http.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CarsController {
    private final ConcurrentHashMap<Integer, Car> cars;

    public CarsController(ConcurrentHashMap<Integer, Car> cars) {
        this.cars = cars;
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
}
