package net.balhar.calendar.service;

import net.balhar.calendar.frontend.Provider;
import net.balhar.calendar.frontend.annotation.Create;
import net.balhar.calendar.frontend.annotation.Delete;
import net.balhar.calendar.frontend.annotation.ResourceCollection;
import net.balhar.calendar.persistence.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * It probably should be Singleton as it actually is unique internal representation of the Calendar. You can have more
 * configured differently.
 */
@Service
public class LarpCalendar implements Calendar, Provider {
    private Set<Event> allEvents;
    private Adapter<Event> persistentStore;

    @Autowired
    public LarpCalendar(Adapter<Event> persistentStore) {
        this.persistentStore = persistentStore;
        persistentStore.setClass(Event.class);
        allEvents = new HashSet<>(persistentStore.retrieve()); // When starting application all the data are loaded
        // into the memory
    }

    @Override
    @ResourceCollection
    public Collection<Event> events(Configuration options) {
        return allEvents.stream()
                .filter(event -> options.from() == null || event.from().isAfter(options.from()))
                .filter(event -> options.to() == null || event.to().isBefore(options.to()))
                .filter(event -> options.location() == null || event.location().equals(options.location()))
                .collect(toList());
    }

    @Override
    public Collection<Event> events() {
        return allEvents;
    }

    @Override
    public boolean contains(Event event) {
        return allEvents.contains(event);
    }

    @Override
    @Create
    public Calendar add(Event event) {
        assertEventIsNotPresent(event);

        allEvents.add(event);
        persistentStore.store(event); // Possibly use replace?

        return this;
    }

    private void assertEventIsNotPresent(Event event) {
        if (allEvents.contains(event)) {
            throw new RuntimeException("Event is already present in the calendar.");
        }
    }

    @Override
    @Delete
    public Calendar remove(Event event) {
        assertEventIsPresent(event);

        allEvents.remove(event);
        persistentStore.remove(event);

        return this;
    }

    private void assertEventIsPresent(Event event) {
        if (!allEvents.contains(event)) {
            throw new RuntimeException("Event is not present in current calendar.");
        }
    }
}
