package tn.esprit.gestioneventlot1.entities;

import jakarta.persistence.*;
import tn.esprit.gestioneventlot1.enums.ProductCategory;

@Entity
@Table(name = "association_needs")
public class AssociationNeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private Double minQuantity;
    private Double maxQuantity;

    // ===== getters & setters =====

    public Long getId() {
        return id;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

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
