package tn.esprit.gestioneventlot1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestioneventlot1.entities.Donation;

import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByReference(String reference);
}
