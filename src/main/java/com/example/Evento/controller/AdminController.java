package com.example.Evento.controller;

import com.example.Evento.model.Event;
import com.example.Evento.service.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventService eventService;

    public AdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication auth) {
        String creatorEmail = auth.getName(); // current logged-in admin
        model.addAttribute("events", eventService.getEventsByCreatorEmail(creatorEmail));
        return "admin/dashboard";
    }

    @GetMapping("/event/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/create";
    }

    @PostMapping("/event")
    public String createEvent(@ModelAttribute Event event, Authentication auth) {
        String creatorEmail = auth.getName();
        eventService.createEvent(event, creatorEmail);
        return "redirect:/admin/dashboard";
    }
}
