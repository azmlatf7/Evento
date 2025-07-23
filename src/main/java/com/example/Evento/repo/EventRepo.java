package com.example.Evento.repo;

import com.example.Evento.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepo extends MongoRepository<Event, String> {
    List<Event> findByCreatorId(String creatorId);
}

