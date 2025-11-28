package tn.esprit.gestioneventlot1.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.gestioneventlot1.dto.EventCreateRequest;
import tn.esprit.gestioneventlot1.dto.EventResponse;
import tn.esprit.gestioneventlot1.services.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/donations/{donationId}/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public EventResponse addEvent(@PathVariable Long donationId,
                                  @RequestBody EventCreateRequest request) {
        return eventService.addEvent(donationId, request);
    }

    @GetMapping
    public List<EventResponse> getEvents(@PathVariable Long donationId) {
        return eventService.getEventsForDonation(donationId);
    }
}
