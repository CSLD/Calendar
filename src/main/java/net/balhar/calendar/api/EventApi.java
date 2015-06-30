package net.balhar.calendar.api;

import net.balhar.api.RestApi;
import net.balhar.calendar.service.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Balda on 30. 6. 2015.
 */
@Controller
@RequestMapping(value = "/events", produces = "application/vnd.api+json")
public class EventApi extends RestApi<Event> {
}
