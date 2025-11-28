package tn.esprit.gestioneventlot1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestioneventlot1.entities.Association;

import java.util.List;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    List<Association> findByActiveTrue();
}
