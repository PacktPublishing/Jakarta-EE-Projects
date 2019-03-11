package org.jakartaeeprojects.coffee.orders.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CoffeeRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty(message = "You must select a drink")
    private String drink;

    @NotNull
    private Size size;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CoffeeRequest{" +
                "email='" + email + '\'' +
                ", drink='" + drink + '\'' +
                ", size=" + size +
                '}';
    }


}
