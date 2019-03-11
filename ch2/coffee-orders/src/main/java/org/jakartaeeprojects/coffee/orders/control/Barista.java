package org.jakartaeeprojects.coffee.orders.control;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.OrderChangeEvent;
import org.jakartaeeprojects.coffee.orders.entity.OrderStatus;

import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Barista {

    @Inject
    private Logger logger;

    @Inject
    private Event<OrderChangeEvent> event;

    private void makeCoffee(@ObservesAsync CoffeeOrder order) {
        logger.log(Level.INFO, "Thread running barista here " + Thread.currentThread().getId());
        slowProcess(2);
        updateStatus(order, OrderStatus.ACCEPTED);
        logger.log(Level.INFO, "Working on {0}", order.getId());

        slowProcess(5);
        updateStatus(order, OrderStatus.READY);
        logger.log(Level.INFO, "Done with {0}", order.getId());
    }

    private void slowProcess(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    private void updateStatus(CoffeeOrder order, OrderStatus accepted) {
        order.setStatus(accepted);
        event.fire(new OrderChangeEvent(order));
    }
}
