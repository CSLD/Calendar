package net.balhar.calendar.impl;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.balhar.calendar.CalendarTest;
import net.balhar.calendar.service.Event;
import net.balhar.calendar.service.LarpCalendar;
import net.balhar.calendar.service.LarpEvent;
import net.balhar.persistence.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Implementation providing JpaCalendar amd LarpEvent as objects under test.
 */
@Transactional
@DatabaseSetup(value = "/net.balhar.calendar/calendar.xml", type = DatabaseOperation.DELETE_ALL)
public class JpaLarpCalendarTest extends CalendarTest {
    @Autowired
    Adapter<Event> adapter;

    @Override
    public void setUp() {
        calendar = new LarpCalendar(adapter);

        skyrim = new LarpEvent(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now(), "Prague");
        arkham = new LarpEvent(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now(), "Dankov");
    }

    @Override
    public void addEventToTheCalendar() {
        super.addEventToTheCalendar();

        assertAmountOfPersisted(1);
    }

    @Override
    public void removePresentEventFromTheCalendar() {
        super.removePresentEventFromTheCalendar();

        assertAmountOfPersisted(0);
    }

    @Override
    public void retrieveAllEventsPresentInCalendar() {
        super.retrieveAllEventsPresentInCalendar();

        assertAmountOfPersisted(2);
    }

    private void assertAmountOfPersisted(int amount) {
        Collection<Event> persistedEvents = adapter.retrieve();
        assertThat(persistedEvents, hasSize(amount));
    }
}
