package com.example.Evento.repo;

import com.example.Evento.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepo extends MongoRepository<Response, String> {
    List<Response> findByEventId(String eventId);
    List<Response> findByUserId(String userId);
    boolean existsByUserIdAndEventId(String userId, String eventId);
}

