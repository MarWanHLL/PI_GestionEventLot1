package tn.esprit.gestioneventlot1.entities;

import jakarta.persistence.*;
import tn.esprit.gestioneventlot1.enums.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")   // événements de suivi pour les dons
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Don concerné par cet événement
    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    private String fromLocation;
    private String toLocation;

    private LocalDateTime timestamp = LocalDateTime.now();

    // commentaire optionnel (ex: "Retard de collecte")
    private String comment;

    public Event() {
    }

    // ===== getters & setters =====

    public Long getId() {
        return id;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
