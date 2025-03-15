package com.example.javaAdvancedExamen.service;

import com.example.javaAdvancedExamen.dto.ReservatieDTO;
import com.example.javaAdvancedExamen.entity.Lokaal;
import com.example.javaAdvancedExamen.entity.Reservatie;
import com.example.javaAdvancedExamen.entity.User;
import com.example.javaAdvancedExamen.exceptions.AlreadyExistsException;
import com.example.javaAdvancedExamen.exceptions.IllegalReservationException;
import com.example.javaAdvancedExamen.exceptions.NotFoundEntityException;
import com.example.javaAdvancedExamen.exceptions.ReservationOverlappingException;
import com.example.javaAdvancedExamen.repository.LokaalRepository;
import com.example.javaAdvancedExamen.repository.ReservatieRepository;
import com.example.javaAdvancedExamen.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservatieService {
    private final ReservatieRepository reservatieRepo;
    private final LokaalRepository lokaalRepo;
    private final UserRepository userRepo;

    public ReservatieService(ReservatieRepository reservatieRepo, LokaalRepository lokaalRepo, UserRepository userRepo) {
        this.reservatieRepo = reservatieRepo;
        this.lokaalRepo = lokaalRepo;
        this.userRepo = userRepo;
    }

    /**
     * Gaat een reservatie toevoegen
     * @param reservatie body van reservatie
     * @return gaat een reservatie aanmaken
     */
    public ReservatieDTO addReservatie(Reservatie reservatie) {//nog checks doen → met tijd(datum)
        validatieReservatieTijd(reservatie);
        return new ReservatieDTO(reservatieRepo.save(reservatie));
    }

    /**
     * Gaat alle reservaties tonen a.d.h.v een user
     * @param id de PK van een user
     * @return gaat alle reservaties tonen
     */
    public List<ReservatieDTO> getAllReservaties(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new NotFoundEntityException(("user met ID: " + id + " werd niet gevonden")));

        if (user.getReservaties().isEmpty()) {
            throw new IllegalArgumentException("Gebruiker: " + id + " heeft geen reservaties");
        }

        return user.getReservaties().stream()
                .map(ReservatieDTO::new).toList();
    }

    /**
     * Gaat een specifieke reservatie tonen a.d.h.v een user
     * @param userId de PK van een user
     * @param id de PK van een reservatie
     * @return gaat de reservering tonen
     */
    public ReservatieDTO getReservatieByUserIdAndReservatieId(Long userId,Long id) {
        Reservatie reservatie = reservatieRepo.findByUserIdAndId(userId,id)
                .orElseThrow(() -> new NotFoundEntityException
                        ("Reservatie met user: " + userId + " voor reservatie: " + id +" werd niet gevonden"));

        return new ReservatieDTO(reservatie);
    }

    /**
     * Gaat een reservering linken aan een lokaal
     * @param userId de PK van een user
     * @param reservatieId de PK van een reservatie
     * @param lokaalId de PK van een lokaal
     * @return gaat de reservering linken aan het gegeven lokaal
     */
    public ReservatieDTO putReservatieToLokaal(Long userId,Long reservatieId, Long lokaalId){
        Reservatie foundReservatie = reservatieRepo.findById(reservatieId)
                .orElseThrow(()-> new NotFoundEntityException("Reservatie: " + reservatieId + " werd niet gevonden"));

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new NotFoundEntityException("User: " + userId + " werd niet gevonden"));

        Lokaal lokaal = lokaalRepo.findById(lokaalId)
                .orElseThrow(()-> new NotFoundEntityException("Lokaal: " + lokaalId + " werd niet gevonden"));

        if (!foundReservatie.getUser().equals(user)) {
            throw new IllegalArgumentException("Reservatie met user: " + userId + " behoort niet toe aan deze gebruiker");
        }

        if (foundReservatie.getLokalen().contains(lokaal)) {
            throw new AlreadyExistsException("Dit lokaal is al toegevoegd aan deze reservatie");
        }

        if (lokaal.getReservaties().contains(foundReservatie)){
            throw new AlreadyExistsException("Deze reservatie bestaat al in dit lokaal");
        }

        if (!reservatieRepo.existsByIdAndStartTijdstipBeforeAndEindeTijdstipAfter(
                lokaal.getId(),
                foundReservatie.getStartTijdstip(),
                foundReservatie.getEindeTijdstip())) {
            throw new ReservationOverlappingException("Deze reservatie overlapt met een ander reservatie in hetzelfde lokaal!");
        }

        foundReservatie.getLokalen().add(lokaal);
        return new ReservatieDTO(reservatieRepo.save(foundReservatie));
    }

    /**
     * Gaat een reservatie verwijderen
     * @param id de PK van een reservering
     */
    public void deleteReservatie(long id) {
        Reservatie reservatie = reservatieRepo.findById(id)
                .orElseThrow(()-> new NotFoundEntityException("Reservatie: " + id + " werd niet gevonden"));
        reservatieRepo.delete(reservatie);
    }

    //Validatie voor start- en eindtijd
    public void validatieReservatieTijd(Reservatie reservatie) {
        LocalDateTime start = reservatie.getStartTijdstip();
        LocalDateTime eind = reservatie.getEindeTijdstip();

        if (start.isAfter(eind)) {
            throw new IllegalReservationException("Starttijd moet voor eindtijd liggen!");
        }

        if (eind.isBefore(LocalDateTime.now())) {
            throw new IllegalReservationException("Je mag niet in het verleden reserveren!");
        }
    }

}
