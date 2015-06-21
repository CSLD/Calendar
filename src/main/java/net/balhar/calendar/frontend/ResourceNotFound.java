package net.balhar.calendar.frontend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource with given uuid doesn't exist.")
public class ResourceNotFound extends RuntimeException {
}
