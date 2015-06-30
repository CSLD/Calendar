package net.balhar.calendar.service;

import net.balhar.jsonapi.Identifiable;

import java.time.LocalDateTime;

/**
 * Common interface for all events. Any user of this package can provide its own implementation of an Event.
 */
public interface Event extends Identifiable {
    /**
     * Unique identifier of an event.
     *
     * @return Unique identifier of the event in its string repreentation.
     */
    String uuid();

    /**
     * It returns date from which the Event starts
     *
     * @return Valid start Date of the event.
     */
    LocalDateTime from();

    /**
     * It returns date on which the Event ends.
     *
     * @return Valid end Date of the event.
     */
    LocalDateTime to();

    /**
     * Every event must be able to provide its own location.
     *
     * @return Valid location of an event.
     */
    String location();
}
