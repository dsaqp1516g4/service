package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.core.Link;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 21/02/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventCollection {

    private List<Link> links;
    private Date firstTimestamp;
    private Date lastTimestamp;
    private List<Event> events = new ArrayList<>();

    public EventCollection() {
        super();
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Date getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(Date firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public Date getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(Date lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
