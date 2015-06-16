package net.balhar.calendar;

import net.balhar.calendar.frontend.RestApi;
import net.balhar.calendar.service.LarpCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LarpCalendarApplication {
    @Autowired
    LarpCalendar larpCalendar;

    public static void main(String[] args) {
        SpringApplication.run(LarpCalendarApplication.class, args);
    }

    @Bean
    public RestApi<LarpCalendar> calendarRestApi() {
        RestApi restApi = new RestApi(larpCalendar, "/");
        return restApi;
    }
}
