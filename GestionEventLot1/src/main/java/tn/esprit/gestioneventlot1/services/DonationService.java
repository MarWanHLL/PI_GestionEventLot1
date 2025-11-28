package tn.esprit.gestioneventlot1.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.gestioneventlot1.dto.DonationCreateRequest;
import tn.esprit.gestioneventlot1.dto.DonationResponse;
import tn.esprit.gestioneventlot1.entities.Donation;
import tn.esprit.gestioneventlot1.enums.DonationStatus;
import tn.esprit.gestioneventlot1.repositories.DonationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public DonationResponse createDonation(DonationCreateRequest request) {
        Donation donation = new Donation();

        // === AUTO-GENERATE REFERENCE ===
        String prefix = "DON-" + request.getCategory().name();
        String uniqueCode = String.valueOf(System.currentTimeMillis()).substring(7);
        String reference = prefix + "-" + uniqueCode;

        donation.setReference(reference);

        donation.setMerchantId(request.getMerchantId());
        donation.setMerchantName(request.getMerchantName());
        donation.setMerchantAddress(request.getMerchantAddress());

        donation.setTotalQuantity(request.getTotalQuantity());
        donation.setQuantityUnit(request.getQuantityUnit());
        donation.setEstimatedValue(request.getEstimatedValue());
        donation.setPickupPlannedAt(request.getPickupPlannedAt());
        donation.setCategory(request.getCategory());

        donation.setStatus(DonationStatus.CREATED);
        donation.setCurrentLocation("Chez le commerçant");

        Donation saved = donationRepository.save(donation);
        return mapToResponse(saved);
    }

    public DonationResponse getDonation(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Donation not found with id " + id));
        return mapToResponse(donation);
    }

    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DonationResponse updateStatus(Long id, DonationStatus status) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Donation not found with id " + id));
        donation.setStatus(status);
        Donation updated = donationRepository.save(donation);
        return mapToResponse(updated);
    }

    private DonationResponse mapToResponse(Donation donation) {
        DonationResponse dto = new DonationResponse();
        dto.setId(donation.getId());
        dto.setReference(donation.getReference());
        dto.setMerchantId(donation.getMerchantId());
        dto.setMerchantName(donation.getMerchantName());
        dto.setMerchantAddress(donation.getMerchantAddress());
        dto.setAssociationId(donation.getAssociationId());
        dto.setAssociationName(donation.getAssociationName());
        dto.setAssociationAddress(donation.getAssociationAddress());
        dto.setStatus(donation.getStatus());
        dto.setCreatedAt(donation.getCreatedAt());
        dto.setPickupPlannedAt(donation.getPickupPlannedAt());
        dto.setDeliveredAt(donation.getDeliveredAt());
        dto.setTotalQuantity(donation.getTotalQuantity());
        dto.setQuantityUnit(donation.getQuantityUnit());
        dto.setEstimatedValue(donation.getEstimatedValue());
        dto.setCurrentLocation(donation.getCurrentLocation());
        dto.setCategory(donation.getCategory());
        return dto;
    }
}
