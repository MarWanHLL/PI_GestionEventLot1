package tn.esprit.gestioneventlot1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestioneventlot1.entities.AssociationNeed;

import java.util.List;

@Repository
public interface AssociationNeedRepository extends JpaRepository<AssociationNeed, Long> {

    List<AssociationNeed> findByAssociationId(Long associationId);
}
