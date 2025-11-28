package tn.esprit.gestioneventlot1.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.gestioneventlot1.dto.DonationCreateRequest;
import tn.esprit.gestioneventlot1.dto.DonationResponse;
import tn.esprit.gestioneventlot1.enums.DonationStatus;
import tn.esprit.gestioneventlot1.services.DonationService;
import tn.esprit.gestioneventlot1.dto.MatchResultResponse;
import tn.esprit.gestioneventlot1.services.MatchingService;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "http://localhost:4200")  // 👈 add this
public class DonationController {

    private final DonationService donationService;
    private final MatchingService matchingService;

    public DonationController(DonationService donationService,
                              MatchingService matchingService) {
        this.donationService = donationService;
        this.matchingService = matchingService;
    }

    @PostMapping("/{id}/match")
    public MatchResultResponse matchDonation(@PathVariable Long id) {
        return matchingService.matchDonation(id);
    }

    @PostMapping
    public DonationResponse createDonation(@RequestBody DonationCreateRequest request) {
        return donationService.createDonation(request);
    }

    @GetMapping
    public List<DonationResponse> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public DonationResponse getDonationById(@PathVariable Long id) {
        return donationService.getDonation(id);
    }

    @PatchMapping("/{id}/status")
    public DonationResponse updateStatus(@PathVariable Long id,
                                         @RequestParam DonationStatus status) {
        return donationService.updateStatus(id, status);
    }
}
