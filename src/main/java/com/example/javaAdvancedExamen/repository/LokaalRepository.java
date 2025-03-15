package com.example.javaAdvancedExamen.repository;

import com.example.javaAdvancedExamen.entity.Lokaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LokaalRepository extends JpaRepository <Lokaal, Long> {
    boolean existsByLokaalNaamAndCampus_CampusNaam(String lokaalNaam, String campusNaam);
    List<Lokaal> findAllByCampus_CampusNaam(String campusNaam);
    Optional<Lokaal> findByIdAndCampus_CampusNaam(Long id,String campusNaam);
}
