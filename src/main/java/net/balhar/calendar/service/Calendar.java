package net.balhar.calendar.service;

import java.util.Collection;

/**
 * It is more or less ResourceCollection of events.
 */
public interface Calendar {
    /**
     * It adds event to current calendar. This is part of fluent API which allows you to chain operations.
     *
     * @param event Event to be added to current calendar.
     * @return Current calendar to chain operation on it.
     */
    Calendar add(Event event);

    /**
     * It removes event from current calendar. It is part of fluent API which allows you to chain operations.
     *
     * @param event Event to be removed from the calendar. If it isn't present throw Exception.
     * @return Current calendar to chain operation on it.
     */
    Calendar remove(Event event);

    /**
     * It updates event in calendar to update event. Possible implementation is through removing current event and
     * replacing it with the new one.
     *
     * @param uuid Uuid of the event to update.
     * @param update Data to replace event with given uuid with.
     * @return
     */
    Calendar update(String uuid, Event update);

    /**
     * It returns all events between two dates. If there is no event in given date range, return empty collection. It is
     * one of the terminal operations to the chain.
     *
     * @param options Options which decides what exactly will be returned.
     * @return ResourceCollection of events in given range. In case there is no events in given range, empty
     * collection is
     * returned
     */
    Collection<Event> events(Configuration options);

    /**
     * Just shorthand for events with default configuration.
     *
     * @see Calendar#events()
     */
    Collection<Event> events();

    /**
     * It verifies whether. It is one of the terminal operation to the chain.
     *
     * @param event Event to be looked up in current calendar.
     * @return True if such event exists in current calendar.
     */
    boolean contains(Event event);
}