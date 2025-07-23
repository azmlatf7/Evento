package com.example.Evento.service;

import com.example.Evento.model.Response;
import com.example.Evento.repo.EventRepo;
import com.example.Evento.repo.ResponseRepo;
import com.example.Evento.model.Event;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private final ResponseRepo responseRepo;
    private final EventRepo eventRepo;

    public ResponseService(ResponseRepo responseRepo, EventRepo eventRepo) {
        this.responseRepo = responseRepo;
        this.eventRepo = eventRepo;
    }

    public boolean hasResponded(String userId, String eventId) {
        return responseRepo.existsByUserIdAndEventId(userId, eventId);
    }

    public void saveResponse(Response response, String userId, String eventId) {
        response.setUserId(userId);
        response.setEventId(eventId);
        response.setSubmittedAt(LocalDateTime.now());
        responseRepo.save(response);
    }

    public List<Response> getResponsesByUser(String userId) {
        return responseRepo.findByUserId(userId);
    }
}
