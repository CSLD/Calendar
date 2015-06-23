package net.balhar.calendar.api;

import net.balhar.calendar.service.Event;
import net.balhar.calendar.service.LarpCalendar;
import net.balhar.calendar.service.LarpConfiguration;
import net.balhar.calendar.service.LarpEvent;
import net.balhar.jsonapi.Document;
import net.balhar.jsonapi.hash.HashDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping(value = "/events", produces = "application/vnd.api+json")
public class RestController {
    private LarpCalendar calendar;

    @Autowired
    public RestController(LarpCalendar calendar) {
        this.calendar = calendar;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity collection(LarpConfiguration configuration) {
        Collection filteredEvents = calendar.events(configuration);

        Document response = new HashDocument(filteredEvents);
        return new ResponseEntity<>(response.transform(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(
            @RequestBody @Validated(Create.class) LarpEvent event
    ) {
        calendar.add(event);

        Document response = new HashDocument(event);
        return new ResponseEntity<>(response.transform(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity update(
            @PathVariable String uuid,
            @RequestBody @Validated(Update.class) LarpEvent event
    ) {
        calendar.remove(event);

    }

}
