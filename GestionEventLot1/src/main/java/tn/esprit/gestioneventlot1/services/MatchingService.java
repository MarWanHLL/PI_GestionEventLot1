package tn.esprit.gestioneventlot1.services;

import org.springframework.stereotype.Service;
import tn.esprit.gestioneventlot1.dto.MatchResultResponse;
import tn.esprit.gestioneventlot1.entities.Association;
import tn.esprit.gestioneventlot1.entities.AssociationNeed;
import tn.esprit.gestioneventlot1.entities.Donation;
import tn.esprit.gestioneventlot1.enums.DonationStatus;
import tn.esprit.gestioneventlot1.repositories.AssociationRepository;
import tn.esprit.gestioneventlot1.repositories.DonationRepository;

import java.util.List;

@Service
public class MatchingService {

    private final AssociationRepository associationRepository;
    private final DonationRepository donationRepository;

    public MatchingService(AssociationRepository associationRepository,
                           DonationRepository donationRepository) {
        this.associationRepository = associationRepository;
        this.donationRepository = donationRepository;
    }

    public MatchResultResponse matchDonation(Long donationId) {

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (donation.getStatus() != DonationStatus.CREATED) {
            throw new RuntimeException("Donation is not in CREATED status, cannot match");
        }

        List<Association> associations = associationRepository.findByActiveTrue();

        Association best = null;
        double bestScore = 0.0;

        for (Association a : associations) {
            double score = computeScore(donation, a);
            if (score > bestScore) {
                bestScore = score;
                best = a;
            }
        }

        MatchResultResponse result = new MatchResultResponse();
        result.setDonationId(donation.getId());

        // ❌ No association scored > 0 → no match, but DO NOT throw
        if (best == null || bestScore <= 0.0) {
            result.setMatched(false);
            result.setAssociationId(null);
            result.setAssociationName(null);
            result.setScore(0.0);
            result.setReason("No suitable association found for this donation");
            return result;
        }

        // ✅ We found a match
        donation.setAssociationId(best.getId());
        donation.setAssociationName(best.getName());
        donation.setAssociationAddress(best.getAddress());
        donation.setStatus(DonationStatus.MATCHED);
        donationRepository.save(donation);

        result.setMatched(true);
        result.setAssociationId(best.getId());
        result.setAssociationName(best.getName());
        result.setScore(bestScore);
        result.setReason("Matching successful");

        return result;
    }

    private double computeScore(Donation donation, Association association) {
        double score = 0.0;

        if (association.getNeeds() == null || association.getNeeds().isEmpty()) {
            return 0.0;
        }

        for (AssociationNeed need : association.getNeeds()) {
            // Category match
            if (donation.getCategory() != null && donation.getCategory() == need.getCategory()) {
                score += 50;

                Double q = donation.getTotalQuantity();
                if (q != null && need.getMaxQuantity() != null) {
                    if (need.getMinQuantity() != null &&
                            q >= need.getMinQuantity() &&
                            q <= need.getMaxQuantity()) {
                        score += 30; // parfait
                    } else if (q <= need.getMaxQuantity() * 1.5) {
                        score += 10; // acceptable
                    }
                }
            }
        }

        // Small bonus if same city (if filled)
        if (donation.getMerchantAddress() != null &&
                association.getCity() != null &&
                donation.getMerchantAddress().toLowerCase()
                        .contains(association.getCity().toLowerCase())) {
            score += 10;
        }

        return score;
    }
}
