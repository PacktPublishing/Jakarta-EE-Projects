package org.jakartaeeprojects.coffee.drinks.boundary;

import org.jakartaeeprojects.coffee.drinks.entity.Coffee;
import org.jakartaeeprojects.coffee.drinks.entity.CoffeeDto;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("coffees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeesResource {

    @Inject
    private Logger logger;

    @Inject
    private CoffeeService coffeeService;

    @GET
    public Response getAll() {
        final List<CoffeeDto> coffeeDtos = coffeeService.getAvailableCoffees();

        return Response.ok(coffeeDtos).build();
    }

    @GET
    @Path("{type}")
    public Response getType(@NotNull @PathParam("type") String type) {
        Optional<CoffeeDto> coffee = coffeeService.getCoffeeByType(type);

        if (!coffee.isPresent()) {
            throw new NotFoundException("Missing coffee " + type);
        }
        return Response.ok(coffee.get()).build();
    }

//    private JsonObject toJson(Coffee coffee) {
//
//        return Json.createObjectBuilder()
//                .add("type", coffee.getType())
//                .add("price", coffee.getPrice().toString())
//                .add("sizes", getSizeAndPrice(coffee))
//                .build();
//    }

    private JsonArray getSizeAndPrice(Coffee coffee) {
//        Map<String, BigDecimal> map = Arrays.stream(Coffee.Size.values())
//                .collect(toMap(Coffee.Size::toString,
//                        s -> s.apply(coffee.getPrice())));

        JsonArrayBuilder builder = Json.createArrayBuilder();
//        builder.add(Coffee.Size.SMALL.apply(coffee.getPrice()).toString());
//        builder.add(Coffee.Size.REGULAR.apply(coffee.getPrice()).toString());
//        builder.add(Coffee.Size.LARGE.apply(coffee.getPrice()).toString());
        return builder.build();
    }
}
