package org.jakartaeeprojects.coffee.drinks.boundary;

import org.jakartaeeprojects.coffee.drinks.entity.Coffee;
import org.jakartaeeprojects.coffee.drinks.entity.CoffeeDto;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Stateless
public class CoffeeService {
    static final List<Coffee> coffeeList = new ArrayList<>();

    //Mimic a DB repo here for brevity sake
    static {
        coffeeList.add(Coffee.create("Flat White", 3.89));
        coffeeList.add(Coffee.create("Mocha", 4.19));
        coffeeList.add(Coffee.create("Cappuccino", 4.19));
        coffeeList.add(Coffee.create("Espresso", 2.35));
        coffeeList.add(Coffee.create("Double Espresso", 3.39));
        coffeeList.add(Coffee.create("Short Macchiato", 3.89));
        coffeeList.add(Coffee.create("Long Macchiato", 4.39));
        coffeeList.add(Coffee.create("Caffe Latte", 3.10));
    }

    public List<CoffeeDto> getAvailableCoffees() {
        return coffeeList.stream()
                .filter(Coffee::isInStock)
                .map(CoffeeDto::new)
                .collect(toList());
    }

    public List<CoffeeDto> getAvailableCoffeesNotSoGoodApproach() {
        //DONT USE THIS
        return coffeeList.stream()
                .filter(c -> c.getStatus() == Coffee.Status.IN_STOCK)
                .map(c -> {
                    CoffeeDto dto = new CoffeeDto();
                    dto.setType(c.getType());
                    dto.setBasePrice(c.getPrice());

                    // more work todo
                    return dto;
                })
                .collect(toList());
    }

    public List<CoffeeDto> getAllCoffees() {
        return coffeeList.stream()
                .map(CoffeeDto::new)
                .collect(toList());
    }

    public Optional<CoffeeDto> getCoffeeByType(final String type) {
        return coffeeList.stream()
                .filter(coffee -> coffee.getType().equalsIgnoreCase(type))
                .map(CoffeeDto::new)
                .findAny();
    }
}
