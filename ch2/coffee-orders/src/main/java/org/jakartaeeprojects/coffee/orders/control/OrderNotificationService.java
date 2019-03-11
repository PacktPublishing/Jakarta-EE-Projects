package org.jakartaeeprojects.coffee.orders.control;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.Mail;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderNotificationService {

    @Inject
    private Logger logger;

    public void notifyOrderStatus(CoffeeOrder order) {
        logger.log(Level.INFO, "notification for {0} ", order);

        Mail email = new Mail();
        email.setTo(order.getCustomerEmail());
        email.setSubject("Order ready");
        email.setBody("Your order is ready");

        send(email);
    }

    private void send(Mail email) {
        //Assume code to send actual email follows next
    }
}

