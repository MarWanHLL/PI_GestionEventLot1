package tn.esprit.gestioneventlot1.services;

import org.springframework.stereotype.Service;
import tn.esprit.gestioneventlot1.dto.*;
import tn.esprit.gestioneventlot1.entities.Association;
import tn.esprit.gestioneventlot1.entities.AssociationNeed;
import tn.esprit.gestioneventlot1.repositories.AssociationNeedRepository;
import tn.esprit.gestioneventlot1.repositories.AssociationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final AssociationNeedRepository needRepository;

    public AssociationService(AssociationRepository associationRepository,
                              AssociationNeedRepository needRepository) {
        this.associationRepository = associationRepository;
        this.needRepository = needRepository;
    }

    public AssociationResponse createAssociation(AssociationCreateRequest request) {
        Association a = new Association();
        a.setName(request.getName());
        a.setAddress(request.getAddress());
        a.setCity(request.getCity());
        a.setZone(request.getZone());
        a.setLocation(request.getLocation());   // 🆕
        a.setActive(true);

        Association saved = associationRepository.save(a);
        return mapToResponse(saved);
    }

    public List<AssociationResponse> getAllAssociations() {
        return associationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AssociationNeedResponse addNeed(Long associationId, AssociationNeedCreateRequest request) {
        Association a = associationRepository.findById(associationId)
                .orElseThrow(() -> new RuntimeException("Association not found"));

        AssociationNeed need = new AssociationNeed();
        need.setAssociation(a);
        need.setCategory(request.getCategory());
        need.setMinQuantity(request.getMinQuantity());
        need.setMaxQuantity(request.getMaxQuantity());

        AssociationNeed saved = needRepository.save(need);
        return mapNeedToResponse(saved);
    }

    public List<AssociationNeedResponse> getNeeds(Long associationId) {
        return needRepository.findByAssociationId(associationId)
                .stream()
                .map(this::mapNeedToResponse)
                .collect(Collectors.toList());
    }

    private AssociationResponse mapToResponse(Association a) {
        AssociationResponse dto = new AssociationResponse();
        dto.setId(a.getId());
        dto.setName(a.getName());
        dto.setAddress(a.getAddress());
        dto.setCity(a.getCity());
        dto.setZone(a.getZone());
        dto.setActive(a.isActive());
        dto.setLocation(a.getLocation());       // 🆕
        return dto;
    }

    private AssociationNeedResponse mapNeedToResponse(AssociationNeed n) {
        AssociationNeedResponse dto = new AssociationNeedResponse();
        dto.setId(n.getId());
        dto.setCategory(n.getCategory());
        dto.setMinQuantity(n.getMinQuantity());
        dto.setMaxQuantity(n.getMaxQuantity());
        return dto;
    }
    // AssociationService.java

    public AssociationResponse getById(Long id) {
        Association a = associationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Association not found"));
        return mapToResponse(a);
    }

    public AssociationResponse updateAssociation(Long id, AssociationCreateRequest request) {
        Association a = associationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Association not found"));

        a.setName(request.getName());
        a.setAddress(request.getAddress());
        a.setCity(request.getCity());
        a.setZone(request.getZone());
        a.setLocation(request.getLocation());
        // you can adjust to allow changing "active" later if you want

        Association updated = associationRepository.save(a);
        return mapToResponse(updated);
    }

    public void deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) {
            throw new RuntimeException("Association not found");
        }
        associationRepository.deleteById(id);
    }


}
