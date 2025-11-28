package tn.esprit.gestioneventlot1.entities;

import jakarta.persistence.*;
import tn.esprit.gestioneventlot1.enums.DonationStatus;
import tn.esprit.gestioneventlot1.enums.ProductCategory;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String reference;

    // Merchant (commerçant)
    @Column(nullable = false)
    private Long merchantId;
    private String merchantName;
    private String merchantAddress;

    // Association (peut être null tant qu'on n'a pas encore fait le matching)
    private Long associationId;
    private String associationName;
    private String associationAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DonationStatus status = DonationStatus.CREATED;

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime pickupPlannedAt;
    private LocalDateTime deliveredAt;

    // résumé global des quantités
    private Double totalQuantity;
    private String quantityUnit;
    private Double estimatedValue;

    // localisation actuelle du don
    private String currentLocation;

    // Liste des événements (tracking) liés à ce don
    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<Event> events;

    public Donation() {
    }

    // ====== getters & setters ======

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getAssociationAddress() {
        return associationAddress;
    }

    public void setAssociationAddress(String associationAddress) {
        this.associationAddress = associationAddress;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPickupPlannedAt() {
        return pickupPlannedAt;
    }

    public void setPickupPlannedAt(LocalDateTime pickupPlannedAt) {
        this.pickupPlannedAt = pickupPlannedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(Double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
