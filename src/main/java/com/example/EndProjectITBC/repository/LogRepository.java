package com.example.EndProjectITBC.repository;

import com.example.EndProjectITBC.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface LogRepository extends JpaRepository<Log, UUID> {
    @Query(value = "SELECT message, log_type, date_of_log FROM log l JOIN client c ON l.clientid = c.clientid WHERE c.clientid = :id AND l.date_of_log >= :dateFrom AND l.date_of_log <= :dateTo ", nativeQuery = true)
    List<String> getAllLogsByClient(@Param("id") UUID id, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query(value = "SELECT message, log_type, date_of_log FROM log l JOIN client c ON l.clientid = c.clientid WHERE c.clientid = :id", nativeQuery = true)
    List<String> getAllLogsById(@Param("id") UUID id);

    @Query(value = "SELECT COUNT (l.clientid) FROM log l JOIN client c ON l.clientid = c.clientid WHERE l.clientid = :id", nativeQuery = true)
    List<Integer> getLogCount(@Param("id") UUID id);
}
