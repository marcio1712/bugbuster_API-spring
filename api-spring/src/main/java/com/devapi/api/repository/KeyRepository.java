package com.devapi.api.repository;

import com.devapi.api.domain.model.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<UserKey, Long> {
}
