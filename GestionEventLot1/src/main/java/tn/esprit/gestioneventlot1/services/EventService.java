package tn.esprit.gestioneventlot1.services;

import org.springframework.stereotype.Service;
import tn.esprit.gestioneventlot1.dto.EventCreateRequest;
import tn.esprit.gestioneventlot1.dto.EventResponse;
import tn.esprit.gestioneventlot1.entities.Donation;
import tn.esprit.gestioneventlot1.entities.Event;
import tn.esprit.gestioneventlot1.repositories.DonationRepository;
import tn.esprit.gestioneventlot1.repositories.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DonationRepository donationRepository;

    public EventService(EventRepository eventRepository, DonationRepository donationRepository) {
        this.eventRepository = eventRepository;
        this.donationRepository = donationRepository;
    }

    public EventResponse addEvent(Long donationId, EventCreateRequest request) {

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found with id " + donationId));

        Event event = new Event();
        event.setDonation(donation);
        event.setType(request.getType());
        event.setFromLocation(request.getFromLocation());
        event.setToLocation(request.getToLocation());
        event.setComment(request.getComment());

        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }

    public List<EventResponse> getEventsForDonation(Long donationId) {
        return eventRepository.findByDonationId(donationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EventResponse mapToResponse(Event event) {
        EventResponse dto = new EventResponse();
        dto.setId(event.getId());
        dto.setType(event.getType());
        dto.setFromLocation(event.getFromLocation());
        dto.setToLocation(event.getToLocation());
        dto.setTimestamp(event.getTimestamp());
        dto.setComment(event.getComment());
        return dto;
    }
}
