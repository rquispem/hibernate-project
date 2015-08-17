package com.app.rquispe.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ruben on 8/14/15.
 */
public class Event {

    private Long id;
    private String title;
    private Date date;
    private Set participants = new HashSet();

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    protected Set getParticipants() {
        return participants;
    }

    protected void setParticipants(Set participants) {
        this.participants = participants;
    }

    /**
     *
     * To prevent error when using bidireccional communication
     */
    public void addToParticipant(Person participant) {
        this.getParticipants().add(participant);
        participant.getEvents().add(this);
    }

    public void removeFromParticipant(Person participant) {
        this.getParticipants().remove(participant);
        participant.getEvents().remove(this);
    }
}
