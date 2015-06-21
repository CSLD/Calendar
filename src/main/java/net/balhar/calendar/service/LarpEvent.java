package net.balhar.calendar.service;

import net.balhar.calendar.persistence.LocalDateTimePersistenceConverter;
import net.balhar.jsonapi.Identifiable;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Simple implementation of event with jpa capabilities
 */
@Entity(name = "event")
@Table(name = "event", schema = "public", catalog = "")
public class LarpEvent implements Event, Identifiable {
    @Column(name = "uuid")
    @Basic
    @Id
    private String uuid;
    @Column(name = "date_from")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime from;
    @Column(name = "date_to")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime to;
    @Column(name = "location")
    @Basic
    private String location;

    public LarpEvent(String uuid, LocalDateTime from, LocalDateTime to, String location) {
        this.from = from;
        this.to = to;
        this.location = location;
        this.uuid = uuid;
    }

    public LarpEvent() {
    }

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public String getUuid() {
        return uuid();
    }

    @Override
    public LocalDateTime from() {
        return from;
    }

    @Override
    public LocalDateTime to() {
        return to;
    }

    @Override
    public String location() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LarpEvent larpEvent = (LarpEvent) o;

        if (from != null ? !from.equals(larpEvent.from) : larpEvent.from != null) return false;
        if (to != null ? !to.equals(larpEvent.to) : larpEvent.to != null) return false;
        return !(location != null ? !location.equals(larpEvent.location) : larpEvent.location != null);

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
