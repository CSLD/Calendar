package net.balhar.calendar.impl;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.balhar.calendar.FilterByTimeTest;
import net.balhar.calendar.service.LarpCalendar;
import net.balhar.calendar.service.LarpEvent;
import net.balhar.persistence.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Test JpaImplementation of the calendar.
 */
@Transactional
@DatabaseSetup(value = "/net.balhar.calendar/calendar.xml", type = DatabaseOperation.DELETE_ALL)
public class JpaLarpFilterByTimeTest extends FilterByTimeTest {
    @Autowired
    Adapter adapter;

    @Override
    public void setUp() {
        calendar = new LarpCalendar(adapter);

        happensNow = new LarpEvent("happensNow", LocalDateTime.now(), LocalDateTime.now(), "Praha");
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minus(2, ChronoUnit.WEEKS).minus(1, ChronoUnit.MINUTES);
        happenedTwoWeeksAgo = new LarpEvent("twoWeeksAgo", twoWeeksAgo, twoWeeksAgo, "Olomouc");
        LocalDateTime weekFuture = LocalDateTime.now().plus(1, ChronoUnit.WEEKS).plus(1, ChronoUnit.MINUTES);
        happensInOneWeek = new LarpEvent("oneWeek", weekFuture, weekFuture, "Brno");
        LocalDateTime monthFuture = LocalDateTime.now().plus(1, ChronoUnit.MONTHS).plus(1, ChronoUnit.MINUTES);
        happensInOneMonth = new LarpEvent("monthFuture", monthFuture, monthFuture, "Plze�");

        super.setUp();
    }
}
