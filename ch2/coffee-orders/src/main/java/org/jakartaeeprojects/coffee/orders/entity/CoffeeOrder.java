package org.jakartaeeprojects.coffee.orders.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class CoffeeOrder {
    private final Long id;

    @FutureOrPresent
    private final LocalDateTime created;
    private OrderStatus status;

    @NotEmpty
    @Email
    private String customerEmail;

    @NotEmpty
    private String drink;
    private Size size;

    public CoffeeOrder(CoffeeRequest request, long id) {
        this.id = id;
        this.created = LocalDateTime.now();
        this.customerEmail = request.getEmail();
        this.drink = request.getDrink();
        this.size = request.getSize();
        this.status = OrderStatus.PLACED;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getDrink() {
        return drink;
    }

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "id=" + id +
                ", created=" + created +
                ", status=" + status +
                ", customerEmail='" + customerEmail + '\'' +
                ", drink='" + drink + '\'' +
                ", size=" + size +
                '}';
    }
}
