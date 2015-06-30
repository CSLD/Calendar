package net.balhar.calendar.service;

import net.balhar.api.Provider;
import net.balhar.api.annotation.*;
import net.balhar.persistence.Adapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static net.balhar.SingletonCollector.singletonCollector;

/**
 * It probably should be Singleton as it actually is unique internal representation of the Calendar. You can have more
 * configured differently.
 */
public class LarpCalendar implements Calendar, Provider {
    private Set<Event> allEvents;
    private Adapter<Event> persistentStore;

    @Autowired
    public LarpCalendar(Adapter<Event> persistentStore) {
        this.persistentStore = persistentStore;
        allEvents = new HashSet<>(persistentStore.retrieve()); // When starting application all the data are loaded
        // into the memory
    }

    @Override
    @ResourceCollection
    public synchronized Collection<Event> events(Configuration options) {
        return allEvents.stream()
                .filter(event -> options.from() == null || event.from().isAfter(options.from()))
                .filter(event -> options.to() == null || event.to().isBefore(options.to()))
                .filter(event -> options.location() == null || event.location().equals(options.location()))
                .collect(toList());
    }

    @Resource
    public synchronized Event single(String uuid) {
        return allEvents.stream()
                .filter(event -> event.uuid().equals(uuid))
                .collect(singletonCollector());
    }

    @Override
    public synchronized Collection<Event> events() {
        return allEvents;
    }

    @Override
    public synchronized boolean contains(Event event) {
        return allEvents.contains(event);
    }

    @Override
    @Create
    public synchronized Calendar add(Event event) {
        assertEventIsNotPresent(event);

        allEvents.add(event);
        persistentStore.store(event); // Possibly use replace?

        return this;
    }

    @Update
    public synchronized Calendar update(String uuid, Event update) {
        Event toUpdate = single(uuid);
        remove(toUpdate);
        add(update);

        return this;
    }

    private void assertEventIsNotPresent(Event event) {
        if (allEvents.contains(event)) {
            throw new RuntimeException("Event is already present in the calendar.");
        }
    }

    @Override
    @Delete
    public synchronized Calendar remove(Event event) {
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
