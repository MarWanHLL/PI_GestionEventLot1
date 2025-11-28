package tn.esprit.gestioneventlot1.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.gestioneventlot1.dto.*;
import tn.esprit.gestioneventlot1.services.AssociationService;

import java.util.List;

@RestController
@RequestMapping("/api/associations")
@CrossOrigin(origins = "http://localhost:4200")
public class AssociationController {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AssociationResponse create(@RequestBody AssociationCreateRequest request) {
        return associationService.createAssociation(request);
    }

    @GetMapping
    public List<AssociationResponse> getAll() {
        return associationService.getAllAssociations();
    }

    // GET /api/associations/{id}
    @GetMapping("/{id}")
    public AssociationResponse getOne(@PathVariable Long id) {
        return associationService.getById(id);
    }

    // PUT /api/associations/{id}
    @PutMapping("/{id}")
    public AssociationResponse update(@PathVariable Long id,
                                      @RequestBody AssociationCreateRequest request) {
        return associationService.updateAssociation(id, request);
    }

    // DELETE /api/associations/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        associationService.deleteAssociation(id);
    }

    @PostMapping("/{id}/needs")
    public AssociationNeedResponse addNeed(@PathVariable Long id,
                                           @RequestBody AssociationNeedCreateRequest request) {
        return associationService.addNeed(id, request);
    }

    @GetMapping("/{id}/needs")
    public List<AssociationNeedResponse> getNeeds(@PathVariable Long id) {
        return associationService.getNeeds(id);
    }
}
