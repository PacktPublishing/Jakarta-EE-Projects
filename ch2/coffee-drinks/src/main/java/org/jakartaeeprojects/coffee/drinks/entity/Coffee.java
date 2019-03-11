package org.jakartaeeprojects.coffee.drinks.entity;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/* Domain object */
public class Coffee {

    private String type;
    private BigDecimal price;
    private Status status = Status.IN_STOCK;
    private List<Size> variants;

    private Coffee(String type) {
        this.type = type;
    }

    public static Coffee create(String type, double price) {
        Coffee coffee = new Coffee(type);
        coffee.price = BigDecimal.valueOf(price);
        coffee.variants = Arrays.asList(Size.values());

        return coffee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Size> getSizeVariants() {
        return variants;
    }

    public void setSizeVariants(List<Size> variants) {
        this.variants = variants;
    }

    public boolean isInStock() {
        return getStatus() == Status.IN_STOCK;
    }

    public BigDecimal calculatePrice(Size size) {
        return size.getExtra().add(price);
    }

    public enum Status {IN_STOCK, UNAVAILABLE}

    public enum Size {
        SMALL(BigDecimal.ZERO),
        REGULAR(BigDecimal.valueOf(2)),
        LARGE(BigDecimal.valueOf(5));

        private BigDecimal extra;

        Size(BigDecimal value) {
            this.extra = value;
        }

        public BigDecimal getExtra() {
            return this.extra;
        }
    }


//    public enum Size {
//        SMALL {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.add(BigDecimal.ZERO);
//            }
//        }, REGULAR {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.add(BigDecimal.TEN);
//            }
//        }, LARGE {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.add(BigDecimal.valueOf(15));
//            }
//        };
//
//        public abstract BigDecimal calculatePrice(BigDecimal drinkPrice);
//    }

//    public enum Size {
//
//        SMALL(  p -> p.add(BigDecimal.ZERO)),
//        REGULAR(p -> p.add(BigDecimal.TEN)),
//        LARGE(  p -> p.add(BigDecimal.valueOf(15)));
//
//        private Function<BigDecimal, BigDecimal> op;
//
//        Size(Function<BigDecimal, BigDecimal> op) {
//            this.op = op;
//        }
//
//        public BigDecimal apply(BigDecimal price) {
//            return this.op.apply(price);
//        }
//
//    }


//        SMALL {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.multiply(BigDecimal.valueOf(2))
//                        .divide(HUNDRED, RoundingMode.HALF_EVEN);
//            }
//        }, REGULAR {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.multiply(BigDecimal.valueOf(5))
//                        .divide(HUNDRED, RoundingMode.HALF_EVEN);
//            }
//        }, LARGE {
//            @Override
//            public BigDecimal calculatePrice(BigDecimal drinkPrice) {
//                return drinkPrice.multiply(BigDecimal.valueOf(10))
//                        .divide(HUNDRED, RoundingMode.HALF_EVEN);
//            }

}
