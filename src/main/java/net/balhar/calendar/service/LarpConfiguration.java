package net.balhar.calendar.service;

import java.time.LocalDateTime;

/**
 * Configuration for Calendar with sane defaults and possibility to override them.
 */
public class LarpConfiguration implements Configuration {
    private LocalDateTime from;
    private LocalDateTime to;
    private String location;
    private String uuid;

    public LarpConfiguration(String uuid, LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
        this.uuid = uuid;
    }

    /**
     * Do nothing. Only provide meaningful defaults for Configuration of the calendar.
     */
    public LarpConfiguration() {
    }

    @Override
    public Configuration from(LocalDateTime from) {
        this.from = from;

        return this;
    }

    @Override
    public Configuration to(LocalDateTime to) {
        this.to = to;

        return this;
    }

    @Override
    public Configuration location(String location) {
        this.location = location;

        return this;
    }


    @Override
    public String uuid() {
        return uuid;
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
    public String getUuid() {
        return uuid;
    }
}
