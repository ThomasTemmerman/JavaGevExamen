package com.example.javaAdvancedExamen.repository;

import com.example.javaAdvancedExamen.entity.Reservatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservatieRepository extends JpaRepository <Reservatie, Long> {
    Optional<Reservatie> findByUserIdAndId(Long userId, Long id);
    boolean existsByIdAndStartTijdstipBeforeAndEindeTijdstipAfter(Long id, LocalDateTime startTijdstip, LocalDateTime eindeTijdstip);
}
