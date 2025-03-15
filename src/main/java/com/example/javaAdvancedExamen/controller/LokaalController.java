package com.example.javaAdvancedExamen.controller;

import com.example.javaAdvancedExamen.dto.CampusDTO;
import com.example.javaAdvancedExamen.dto.LokaalDTO;
import com.example.javaAdvancedExamen.dto.ReservatieDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.entity.Lokaal;
import com.example.javaAdvancedExamen.exceptions.GlobalExceptionHandler;
import com.example.javaAdvancedExamen.service.CampusService;
import com.example.javaAdvancedExamen.service.LokaalService;
import com.example.javaAdvancedExamen.service.ReservatieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus")//VOOR ALLE CONTROLLERS HTTP STATUSSEN PLUS NOG EXCEPTIONS OPVANGEN
public class LokaalController extends GlobalExceptionHandler {
    private final LokaalService lokaalService;
    private final CampusService campusService;

    public LokaalController(LokaalService lokaalService, CampusService campusService, ReservatieService reservatieService) {
        this.lokaalService = lokaalService;
        this.campusService = campusService;
    }

    @Operation(summary = "findByLokaalIdAndCampusId")
    @GetMapping("/{campus-id}/rooms/{lokaal-id}")
    public ResponseEntity<LokaalDTO> getLokaalByIdAndCampusId(
            @PathVariable("campus-id")String campusNaam,
            @PathVariable("lokaal-id")Long id) {
        LokaalDTO lokaalDTO = lokaalService.getLokaalByIdAndCampus(id,campusNaam);
        return ResponseEntity.ok(lokaalDTO);
    }

    @Operation(summary = "getLokaalByIdAndShowReservations")
    @GetMapping("/{campus-id}/rooms/{lokaal-id}/reservations")
    public ResponseEntity<List<ReservatieDTO>> getLokaalByIdAndShowsReservations(
            @PathVariable("campus-id")String campusNaam,
            @PathVariable("lokaal-id")Long lokaalId) {
        List<ReservatieDTO> reservaties = lokaalService.getAllReservatieForLokaal(lokaalId,campusNaam);
        return ResponseEntity.ok(reservaties);
    }

    @Operation(summary = "getAllLokalenByCampus")
    @GetMapping("/{campus-id}/rooms")
    public ResponseEntity<List<LokaalDTO>> getAllLokalenByCampus(
            @PathVariable("campus-id") String campusNaam) {
        List<LokaalDTO> lokaalDTO = lokaalService.getAllLokalenByCampus(campusNaam);
        return ResponseEntity.ok(lokaalDTO);
    }

    @Operation(summary = "createLokaal")
    @PostMapping("/{campus-id}/rooms")
    public ResponseEntity<LokaalDTO> createLokaal(
            @PathVariable("campus-id") String campusNaam,
            @RequestBody Lokaal lokaal) {
        CampusDTO campusDTO = campusService.getCampusById(campusNaam);
        Campus campus = campusService.convertToEntity(campusDTO);
        lokaal.setCampus(campus);
        LokaalDTO newLokaal = lokaalService.addLokaal(lokaal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLokaal);
    }

    @Operation(summary = "editLokaal")
    @PutMapping("/{campus-id}/rooms/{lokaal-id}")
    public ResponseEntity<LokaalDTO> editLokaal(
            @PathVariable("campus-id") String campusNaam,
            @PathVariable("lokaal-id") Long id,
            @RequestBody Lokaal lokaal) {
        CampusDTO campusDTO = campusService.getCampusById(campusNaam);
        Campus campus = campusService.convertToEntity(campusDTO);
        lokaal.setCampus(campus);
        LokaalDTO updatedLokaal = lokaalService.editLokaal(id, lokaal);
        return ResponseEntity.ok(updatedLokaal);
    }

    @Operation(summary = "deleteLokaal")
    @DeleteMapping("/{campus-id}/rooms/{lokaal-id}")
    public ResponseEntity<String> deleteLokaal(
            @PathVariable("lokaal-id")Long id) {
        lokaalService.deleteLokaal(id);
        return ResponseEntity.ok("Succesvol verwijderd!");
    }
}
