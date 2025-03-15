package com.example.javaAdvancedExamen.controller;

import com.example.javaAdvancedExamen.dto.CampusDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.exceptions.GlobalExceptionHandler;
import com.example.javaAdvancedExamen.service.CampusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController extends GlobalExceptionHandler {
    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }
    @Operation(summary = "findCampusById")
    @GetMapping("/{campus-id}")
    public ResponseEntity<CampusDTO> getCampusById(@PathVariable("campus-id") String campusNaam) {
        CampusDTO campusDTO = campusService.getCampusById(campusNaam);
        return ResponseEntity.ok(campusDTO);
    }

    @Operation(summary = "getAllCampus")
    @GetMapping("/")
    public ResponseEntity<List<CampusDTO>> getAllCampus() {
        List<CampusDTO> campusDTO = campusService.getAllCampuses();
        return ResponseEntity.ok(campusDTO);
    }

    @Operation(summary = "createCampus")
    @PostMapping("/")
    public ResponseEntity<CampusDTO> createCampus(@RequestBody Campus campus) {
            CampusDTO newCampus = campusService.addCampus(campus);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCampus);
    }

    @Operation(summary = "editCampus")
    @PutMapping("{campus-id}")//nog checken! + postman
    public ResponseEntity<CampusDTO> editCampus(
            @PathVariable("campus-id") String campusNaam,
            @RequestBody Campus campus) {
        CampusDTO campusDTO = campusService.editCampus(campusNaam,campus);
        return ResponseEntity.ok(campusDTO);
    }

    @Operation(summary = "deleteCampus")
    @DeleteMapping("/{campus-id}")
    public ResponseEntity<String> deleteCampus(@PathVariable("campus-id") String campusNaam) {
        campusService.deleteCampus(campusNaam);
        return ResponseEntity.ok("Succesvol verwijderd!");
    }
}
