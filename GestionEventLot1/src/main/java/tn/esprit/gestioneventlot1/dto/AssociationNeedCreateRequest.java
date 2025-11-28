package tn.esprit.gestioneventlot1.dto;

import tn.esprit.gestioneventlot1.enums.ProductCategory;

public class AssociationNeedCreateRequest {

    private ProductCategory category;
    private Double minQuantity;
    private Double maxQuantity;

    // getters & setters

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Double maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
