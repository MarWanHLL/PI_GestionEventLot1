package tn.esprit.gestioneventlot1.dto;

import tn.esprit.gestioneventlot1.enums.DonationStatus;
import tn.esprit.gestioneventlot1.enums.ProductCategory;

import java.time.LocalDateTime;

public class DonationResponse {

    private Long id;
    private String reference;

    private Long merchantId;
    private String merchantName;
    private String merchantAddress;

    private Long associationId;
    private String associationName;
    private String associationAddress;

    private DonationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime pickupPlannedAt;
    private LocalDateTime deliveredAt;

    private Double totalQuantity;
    private String quantityUnit;
    private Double estimatedValue;

    private String currentLocation;

    private ProductCategory category;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getMerchantAddress() { return merchantAddress; }
    public void setMerchantAddress(String merchantAddress) { this.merchantAddress = merchantAddress; }

    public Long getAssociationId() { return associationId; }
    public void setAssociationId(Long associationId) { this.associationId = associationId; }

    public String getAssociationName() { return associationName; }
    public void setAssociationName(String associationName) { this.associationName = associationName; }

    public String getAssociationAddress() { return associationAddress; }
    public void setAssociationAddress(String associationAddress) { this.associationAddress = associationAddress; }

    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getPickupPlannedAt() { return pickupPlannedAt; }
    public void setPickupPlannedAt(LocalDateTime pickupPlannedAt) { this.pickupPlannedAt = pickupPlannedAt; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }

    public Double getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Double totalQuantity) { this.totalQuantity = totalQuantity; }

    public String getQuantityUnit() { return quantityUnit; }
    public void setQuantityUnit(String quantityUnit) { this.quantityUnit = quantityUnit; }

    public Double getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(Double estimatedValue) { this.estimatedValue = estimatedValue; }

    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
}
