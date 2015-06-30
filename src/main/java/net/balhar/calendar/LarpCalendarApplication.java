package net.balhar.calendar;

import net.balhar.api.annotation.AnnotationProxy;
import net.balhar.calendar.api.EventApi;
import net.balhar.calendar.service.Event;
import net.balhar.calendar.service.LarpCalendar;
import net.balhar.calendar.service.LarpConfiguration;
import net.balhar.persistence.Adapter;
import net.balhar.persistence.JpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class LarpCalendarApplication {
    @Autowired
    private EntityManager persistenceStore;
    @Autowired
    private EventApi eventApi;

    public static void main(String[] args) {
        SpringApplication.run(LarpCalendarApplication.class, args);
    }

    @Bean
    public Adapter<Event> eventAdapter() {
        Adapter<Event> adapter = new JpaAdapter<>(persistenceStore);
        adapter.setClass(Event.class);
        return adapter;
    }

    @Bean
    public LarpCalendar calendarProvider() {
        LarpCalendar calendar = new LarpCalendar(eventAdapter());

        AnnotationProxy proxy = new AnnotationProxy(calendar);
        eventApi.setProxy(proxy);
        eventApi.setConfiguration(LarpConfiguration.class);

        return calendar;
    }
}
