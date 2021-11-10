package org.syphr.wordplay.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class EventBus
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private static final com.google.common.eventbus.EventBus bus = new com.google.common.eventbus.EventBus();
    static
    {
        register(new DeadEventLogger());
    }

    public static void register(Object object)
    {
        LOGGER.trace("Registering event handler: {}", object.getClass().getName());
        bus.register(object);
    }

    public static void unregister(Object object)
    {
        LOGGER.trace("Unregistering event handler: {}", object.getClass().getName());
        bus.unregister(object);
    }

    public static void post(Object event)
    {
        LOGGER.trace("Posting event: {}", event.getClass().getName());
        bus.post(event);
    }

    private EventBus()
    {
        /*
         * Static utilities
         */
    }

    private static class DeadEventLogger
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(DeadEventLogger.class);

        @Subscribe
        public void logDeadEvent(DeadEvent event)
        {
            LOGGER.debug("Event not handled: {}", event.getEvent().getClass().getName());
        }
    }
}
