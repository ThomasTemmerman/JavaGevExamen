package com.example.javaAdvancedExamen.service;

import com.example.javaAdvancedExamen.dto.LokaalDTO;
import com.example.javaAdvancedExamen.dto.ReservatieDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.entity.Lokaal;
import com.example.javaAdvancedExamen.exceptions.AlreadyExistsException;
import com.example.javaAdvancedExamen.exceptions.NotFoundEntityException;
import com.example.javaAdvancedExamen.repository.CampusRepository;
import com.example.javaAdvancedExamen.repository.LokaalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LokaalService {
    private final LokaalRepository repo;
    private final CampusRepository campusRepo;

    public LokaalService(LokaalRepository repo, CampusRepository campusRepo) {
        this.repo = repo;
        this.campusRepo = campusRepo;
    }

    /**
     * Gaat een lokaal toevoegen en linken met een campus
     * @param lokaal lokaal body
     * @return gaat het nieuwe lokaal aanmaken
     */
    public LokaalDTO addLokaal(Lokaal lokaal) {
        if (repo.existsByLokaalNaamAndCampus_CampusNaam(
                lokaal.getLokaalNaam(),
                lokaal.getCampus().getCampusNaam())) {
            throw new AlreadyExistsException(
                    "Lokaal met naam: " + lokaal.getLokaalNaam() + " bestaat al in deze campus");
        }

        return new LokaalDTO(repo.save(lokaal));
    }

    /**
     * Gaat een lokaal wijzigen
     * @param id de PK van lokaal opzoeken
     * @param lokaal lokaal body
     * @return gaat de wijzigingen toepassen
     */
    public LokaalDTO editLokaal(Long id, Lokaal lokaal) {
        Lokaal foundLokaal = repo.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Lokaal werd niet gevonden"));

        if (lokaal.getCampus() == null || lokaal.getCampus().getCampusNaam() == null)  {
            throw new NullPointerException("Campus mag niet leeg zijn");
        }

        Campus campus = campusRepo.findByCampusNaam(lokaal.getCampus().getCampusNaam())
                .orElseThrow(() -> new NotFoundEntityException("Campus werd niet gevonden"));

        if (!foundLokaal.getLokaalNaam().equals(lokaal.getLokaalNaam()) &&
                repo.existsByLokaalNaamAndCampus_CampusNaam(
                        lokaal.getLokaalNaam(),
                        campus.getCampusNaam())) {
            throw new AlreadyExistsException(
                    "Lokaal met naam: " + lokaal.getLokaalNaam() + " bestaat al in deze campus");
        }

        foundLokaal.setTypeLokaal(lokaal.getTypeLokaal());
        foundLokaal.setCapaciteit(lokaal.getCapaciteit());
        foundLokaal.setVerdieping(lokaal.getVerdieping());
        foundLokaal.setAchterNaam(lokaal.getAchterNaam());
        foundLokaal.setCampus(lokaal.getCampus());

        return new LokaalDTO(repo.save(foundLokaal));
    }

    /**
     * Gaat een lokaal verwijderen
     * @param id de PK van een lokaal
     */
    public void deleteLokaal(Long id) {
        Lokaal lokaal = repo.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Lokaal werd niet gevonden"));
        repo.delete(lokaal);
    }

    /**
     * Gaat alle reservaties van een bepaalde lokaal tonen
     * @param id de PK van een lokaal
     * @param campusNaam campus naam
     * @return gaat alle reservaties teruggeven in een lijst
     */
    public List<ReservatieDTO> getAllReservatieForLokaal(Long id, String campusNaam) {
        Lokaal lokaal = repo.findByIdAndCampus_CampusNaam(id,campusNaam)
                .orElseThrow(() -> new NotFoundEntityException("Lokaal werd niet gevonden in deze campus"));

        if (lokaal.aantalReservaties() <= 0) {
            throw new IllegalArgumentException("Dit lokaal heeft geen reservatie");
        }

        return lokaal.getReservaties()
                .stream()
                .map(ReservatieDTO::new)
                .toList();
    }

    /**
     * Gaat alle lokalen tonen die toe behoren tot een bepaalde campus
     * @param campusNaam campus naam
     * @return gaat alle lokalen teruggeven
     */
    public List<LokaalDTO> getAllLokalenByCampus(String campusNaam) {
        Campus campus = campusRepo.findById(campusNaam)
                .orElseThrow(() -> new NotFoundEntityException("Campus: " + campusNaam + " bestaat niet"));
        if (campus.getLokalen().isEmpty()) {
            throw new IllegalArgumentException("Dit campus heeft geen lokalen");
        }
        try {
            return repo.findAllByCampus_CampusNaam(campusNaam)
                    .stream()
                    .map(LokaalDTO::new)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Fout bij het ophalen van lokalen: " + e.getMessage());
        }
    }

    /**
     * Gaat een lokaal en campus tonen a.d.h.v de PK's
     * @param id de PK van een lokaal
     * @param campusNaam de naam van een campus
     * @return gaat het lokaal weergeven
     */
    public LokaalDTO getLokaalByIdAndCampus(Long id, String campusNaam) {
        Lokaal lokaal = repo.findByIdAndCampus_CampusNaam(id,campusNaam)
                .orElseThrow(() -> new NotFoundEntityException("Lokaal werd niet gevonden in deze campus"));
        return new LokaalDTO(lokaal);
    }
}
