package com.example.springevent.repository;

import com.example.springevent.domain.TicketCache;
import org.springframework.data.repository.CrudRepository;

public interface RedisCacheRepository extends CrudRepository<TicketCache, Long> {
}
