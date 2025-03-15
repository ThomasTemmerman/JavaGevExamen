package com.example.javaAdvancedExamen.service;

import com.example.javaAdvancedExamen.dto.CampusDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.exceptions.AlreadyExistsException;
import com.example.javaAdvancedExamen.exceptions.NotFoundEntityException;
import com.example.javaAdvancedExamen.repository.CampusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CampusService {

    private final CampusRepository repo;

    public CampusService(CampusRepository repo) {
        this.repo = repo;
    }

    /**
     * Gaat een nieuwe campus aanmaken
     * @param campus campus body
     * @return gaat campus aanmaken
     */
    public CampusDTO addCampus(Campus campus) {
        if (repo.existsById(campus.getCampusNaam())) {
            throw new AlreadyExistsException("Campus bestaat al");
        }

        return new CampusDTO(repo.save(campus));
    }

    /**
     * Gaat een campus wijzigen
     * @param campusNaam naam van campus
     * @param campus campus body
     * @return gaat de wijzigingen toepassen
     */
    public CampusDTO editCampus(String campusNaam,Campus campus) {
        Campus foundCampus = repo.findById(campusNaam)
                .orElseThrow(() -> new NotFoundEntityException("Campus werd niet gevonden"));

        if (!campusNaam.equals(campus.getCampusNaam()) && repo.existsById(campus.getCampusNaam())) {
            throw  new AlreadyExistsException("Nieuwe campus naam: " + campusNaam + " bestaat al");
        }
        foundCampus.setAdres(campus.getAdres());
        foundCampus.setAantalParkeerPlaatsen(campus.getAantalParkeerPlaatsen());

        return new CampusDTO(repo.save(foundCampus));
    }

    /**
     * Gaat een campus verwijderen
     * @param campusNaam naam van campus
     */
    public void deleteCampus(String campusNaam) {
        Campus campus = repo.findById(campusNaam)
                .orElseThrow(()-> new NotFoundEntityException("Campus werd niet gevonden"));

        if (!campus.getLokalen().isEmpty()) {//gaat pas verwijderen als geen lokalen heeft
            throw new IllegalArgumentException("Kan campus niet verwijderen, eerst lokalen verwijderen");
        }

        repo.delete(campus);
    }

    /**
     * Gaat alle campussen oproepen
     * @return gaat de campussen terug sturen in een lijst
     */
    public List<CampusDTO> getAllCampuses() {
        try {
            return repo.findAll().stream()
                    .map(CampusDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Fout bij het ophalen van alle campussen: " + e.getMessage());
        }
    }

    /**
     * Gaat een campus opzoek a.d.h.v de naam (PK)
     * @param campusNaam campus naam
     * @return gaat de gevonden campus terug geven
     */
    public CampusDTO getCampusById(String campusNaam) {
        Campus campus = repo.findById(campusNaam)
                .orElseThrow(() -> new NotFoundEntityException("Campus werd niet gevonden"));
        return new CampusDTO(campus);
    }

    //omzetten naar entiteit om met database te werken (endpoint)
    public Campus convertToEntity(CampusDTO campusDTO) {
        Campus campus = new Campus();
        campus.setCampusNaam(campusDTO.getCampusNaam());
        campus.setAdres(campusDTO.getAdres());
        campus.setAantalParkeerPlaatsen(campusDTO.getAantalParkeerPlaatsen());
        return campus;
    }

}
