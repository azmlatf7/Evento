package com.example.Evento.service;

import com.example.Evento.model.Event;
import com.example.Evento.model.User;
import com.example.Evento.repo.EventRepo;
import com.example.Evento.repo.UserRepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    public EventService(EventRepo eventRepo, UserRepo userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public List<Event> getEventsByCreatorEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return eventRepo.findByCreatorId(user.getId());
    }

    public void createEvent(Event event, String creatorEmail) {
        User user = userRepo.findByEmail(creatorEmail).orElseThrow();
        event.setCreatorId(user.getId());
        eventRepo.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventRepo.findById(id);
    }

    public Map<String, String> mapEventIdsToTitles() {
        return eventRepo.findAll().stream()
                .collect(Collectors.toMap(Event::getId, Event::getTitle));
    }

}

