package tn.esprit.gestioneventlot1.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "associations")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String zone;       // simple geographic zone (centre, nord, sud…)


    private String location;

    private boolean active = true;

    @OneToMany(mappedBy = "association", cascade = CascadeType.ALL)
    private List<AssociationNeed> needs;

    // ===== getters & setters =====

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZone() {
        return zone;
    }

    public String getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public List<AssociationNeed> getNeeds() {
        return needs;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setLocation(String location) {  // 🆕
        this.location = location;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setNeeds(List<AssociationNeed> needs) {
        this.needs = needs;
    }
}
