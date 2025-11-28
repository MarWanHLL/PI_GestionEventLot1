package tn.esprit.gestioneventlot1.dto;

import tn.esprit.gestioneventlot1.enums.ProductCategory;

import java.time.LocalDateTime;

public class DonationCreateRequest {

    private String reference;
    private Long merchantId;
    private String merchantName;
    private String merchantAddress;

    private Double totalQuantity;
    private String quantityUnit;
    private Double estimatedValue;

    private LocalDateTime pickupPlannedAt;

    private ProductCategory category;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
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

    public LocalDateTime getPickupPlannedAt() {
        return pickupPlannedAt;
    }

    public void setPickupPlannedAt(LocalDateTime pickupPlannedAt) {
        this.pickupPlannedAt = pickupPlannedAt;
    }
}
