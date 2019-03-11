package org.jakartaeeprojects.coffee.orders.boundary;

import org.jakartaeeprojects.coffee.orders.entity.CoffeeOrder;
import org.jakartaeeprojects.coffee.orders.entity.OrderChangeEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Path("order-events")
public class OrderEventsResource {

    @Inject
    private Logger logger;

    @Context
    private Sse sse;

    private SseBroadcaster broadcaster;

    @PostConstruct
    private void init() {
        this.broadcaster = this.sse.newBroadcaster();
    }

    //Subscribe
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void streamEvents(@Context SseEventSink sseEventSink) {
        this.broadcaster.register(sseEventSink);
    }

    //Observe order change events and broadcast
    public void onOrderChange(@Observes OrderChangeEvent orderEvent) {
        logger.log(Level.INFO, "Broadcasting order update {0}", orderEvent.getOrder().getId());

        broadcast(orderEvent.getOrder());
    }

    private void broadcast(CoffeeOrder order) {
        if (this.broadcaster == null) {
            logger.log(Level.SEVERE, "Broadcaster missing");
            return;
        }

        OutboundSseEvent outboundSseEvent = buildEvent(order);
        try {
            this.broadcaster.broadcast(outboundSseEvent);
        } catch(Exception ex) {
            logger.log(Level.SEVERE, "Failed to broadcast order {0}", order.getId());
        }
    }

    private OutboundSseEvent buildEvent(CoffeeOrder order) {
        return this.sse.newEventBuilder()
                .name("order")
                .id(order.getId().toString())
                .data(CoffeeOrder.class, order)
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
