package tn.esprit.gestioneventlot1.dto;

import tn.esprit.gestioneventlot1.enums.EventType;

public class EventCreateRequest {

    private EventType type;
    private String fromLocation;
    private String toLocation;
    private String comment;

    // getters & setters

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
