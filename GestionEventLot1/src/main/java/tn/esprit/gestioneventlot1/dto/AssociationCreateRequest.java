package tn.esprit.gestioneventlot1.dto;

public class AssociationCreateRequest {

    private String name;
    private String address;
    private String city;
    private String zone;
    private String location;   // 🆕 new field

    // getters & setters

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

    public String getLocation() {          // 🆕
        return location;
    }

    public void setLocation(String location) {  // 🆕
        this.location = location;
    }
}
