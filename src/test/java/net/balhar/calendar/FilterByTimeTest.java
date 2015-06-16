package net.balhar.calendar;

import net.balhar.calendar.service.Calendar;
import net.balhar.calendar.service.Configuration;
import net.balhar.calendar.service.Event;
import net.balhar.calendar.service.LarpConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Acceptance tests for filtering by the date.
 */
public abstract class FilterByTimeTest extends AcceptanceTest {
    protected Calendar calendar;

    protected Event happensInOneWeek;
    protected Event happenedTwoWeeksAgo;
    protected Event happensInOneMonth;
    protected Event happensNow;

    @Before
    public void setUp() {
        calendar.add(happenedTwoWeeksAgo);
        calendar.add(happensInOneMonth);
        calendar.add(happensInOneWeek);
        calendar.add(happensNow);
    }

    @Test
    public void retrieveOnlyFutureEvents() {
        Configuration futureEventsOnly = new LarpConfiguration().from(LocalDateTime.now());
        Collection<Event> futureEvents = calendar.events(futureEventsOnly);
        assertThat(futureEvents, hasSize(2));
    }

    @Test
    public void retrieveOnlyPastEvents() {
        Configuration pastEventsOnly = new LarpConfiguration().to(LocalDateTime.now());
        Collection<Event> pastEvents = calendar.events(pastEventsOnly);
        assertThat(pastEvents, hasSize(2));
    }

    @Test
    public void retrieveInWeekOneWeekInFuture() {
        LocalDateTime weekInFuture = LocalDateTime.now().plus(1, ChronoUnit.WEEKS);
        LocalDateTime twoWeeksInFuture = LocalDateTime.now().plus(2, ChronoUnit.WEEKS);
        Configuration betweenOneWeekAndTwoWeeksInFuture = new LarpConfiguration().from(weekInFuture).to
                (twoWeeksInFuture);
        Collection<Event> futureOneToTwoWeeks = calendar.events(betweenOneWeekAndTwoWeeksInFuture);
        assertThat(futureOneToTwoWeeks, hasSize(1));
    }
}
