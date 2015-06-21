package net.balhar.calendar.frontend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason = "Operation isn't possible on given resource.")
public class Unsupported extends RuntimeException {
}
