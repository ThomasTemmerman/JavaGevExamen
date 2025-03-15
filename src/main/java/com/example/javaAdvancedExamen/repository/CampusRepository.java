package com.example.javaAdvancedExamen.repository;

import com.example.javaAdvancedExamen.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampusRepository extends JpaRepository<Campus, String> {
    Optional<Campus> findByCampusNaam(String campusNaam);
}
