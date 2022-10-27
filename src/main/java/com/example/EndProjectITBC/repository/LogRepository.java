package com.example.EndProjectITBC.repository;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface LogRepository extends JpaRepository<Log, UUID> {
   @Query(value = "SELECT message,date_of_log,log_type FROM log l JOIN client c ON l.clientid = c.clientid WHERE c.clientid = :id", nativeQuery = true)
    Log getAllLogsByClient(@Param("id") UUID id);
}
