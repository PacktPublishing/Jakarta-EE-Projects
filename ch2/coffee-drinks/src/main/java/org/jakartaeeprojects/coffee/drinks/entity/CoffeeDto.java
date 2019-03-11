package org.jakartaeeprojects.coffee.drinks.entity;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/* Domain object that is not persisted. Think transient entities */
public class CoffeeDto {

//    public static final Map<Coffee.Size, BigDecimal> VARIANT_EXTRA_PRICE = Stream.of(Coffee.Size.values())
//            .collect(toMap(s -> s, Coffee.Size::getExtra));

    private String type;
    private BigDecimal price;
    private Map<Coffee.Size, BigDecimal> variantPrice;

    public CoffeeDto() {
    }

    public CoffeeDto(Coffee coffee) {
        this.type = coffee.getType();
        this.price = coffee.getPrice();

//        this.variantPrice = coffee.getSizeVariants().stream().collect(
//                toMap(s -> s, s -> s.getExtra().add(coffee.getPrice()))
//        );

        this.variantPrice = coffee.getSizeVariants().stream().collect(
                toMap(s -> s, coffee::calculatePrice));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBasePrice() {
        return price;
    }

    public void setBasePrice(BigDecimal price) {
        this.price = price;
    }

    public Map<Coffee.Size, BigDecimal> getVariantPrice() {
        return variantPrice;
    }
}
