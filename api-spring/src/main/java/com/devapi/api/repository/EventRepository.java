package com.devapi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devapi.api.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
}
