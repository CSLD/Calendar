package net.balhar.calendar.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity with given uuid wasn't found.")
public class NotFoundException extends RuntimeException {
}
