package tn.esprit.gestioneventlot1.enums;

public enum DonationStatus {
    CREATED,        // don créé par le commerçant
    MATCHED,        // une association a été trouvée
    IN_TRANSIT,     // en cours de transport
    DELIVERED,      // livré à l'association
    CANCELLED       // annulé
}
