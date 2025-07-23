package com.example.Evento.controller;

import com.example.Evento.model.Event;
import com.example.Evento.model.Response;
import com.example.Evento.service.EventService;
import com.example.Evento.service.ResponseService;
import com.example.Evento.model.User;
import com.example.Evento.repo.UserRepo;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/participant")
public class ParticipantController {

    private final EventService eventService;
    private final ResponseService responseService;
    private final UserRepo userRepo;

    public ParticipantController(EventService eventService, ResponseService responseService, UserRepo userRepo) {
        this.eventService = eventService;
        this.responseService = responseService;
        this.userRepo = userRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "participant/dashboard";
    }

    @GetMapping("/event/{id}")
    public String showEventForm(@PathVariable String id, Model model, Authentication auth) {
        Optional<Event> eventOpt = eventService.getEventById(id);
        if (eventOpt.isEmpty()) return "redirect:/participant/dashboard";

        String userEmail = auth.getName();
        String userId = userRepo.findByEmail(userEmail).orElseThrow().getId();

        if (responseService.hasResponded(userId, id)) {
            return "redirect:/participant/events";
        }

        model.addAttribute("event", eventOpt.get());
        model.addAttribute("response", new Response());
		return "participant/form";
    }

    @PostMapping("/event/{id}")
    public String submitResponse(@PathVariable String id, @ModelAttribute Response response, Authentication auth) {
        String userEmail = auth.getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow();
        responseService.saveResponse(response, user.getId(), id);
        return "redirect:/participant/events";
    }

    @GetMapping("/events")
    public String viewJoinedEvents(Model model, Authentication auth) {
        String userEmail = auth.getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow();
        model.addAttribute("responses", responseService.getResponsesByUser(user.getId()));
        model.addAttribute("eventsMap", eventService.mapEventIdsToTitles());
        return "participant/events";
    }
}

