package tn.esprit.gestioneventlot1.dto;

public class AssociationResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String zone;
    private boolean active;
    private String location;   // 🆕

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLocation() {          // 🆕
        return location;
    }

    public void setLocation(String location) {  // 🆕
        this.location = location;
    }
}
