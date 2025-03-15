package com.example.javaAdvancedExamen.controller;

import com.example.javaAdvancedExamen.dto.ReservatieDTO;
import com.example.javaAdvancedExamen.dto.UserDTO;
import com.example.javaAdvancedExamen.entity.Reservatie;
import com.example.javaAdvancedExamen.entity.User;
import com.example.javaAdvancedExamen.exceptions.GlobalExceptionHandler;
import com.example.javaAdvancedExamen.service.ReservatieService;
import com.example.javaAdvancedExamen.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ReservatieController extends GlobalExceptionHandler {
    private final ReservatieService reservatieService;
    private final UserService userService;

    public ReservatieController(ReservatieService reservatieService,UserService userService) {
        this.reservatieService = reservatieService;
        this.userService = userService;
    }

    @Operation(summary = "getAllReservatiesByUser")
    @GetMapping("/{user-id}/reservations")
    public ResponseEntity<List<ReservatieDTO>> getAllReservatiesByUser(
            @PathVariable("user-id") Long id) {
        List<ReservatieDTO> reservatieDTO = reservatieService.getAllReservaties(id);
        return ResponseEntity.ok(reservatieDTO);
    }

    @Operation(summary = "findReservatieById")
    @GetMapping("{user-id}/reservations/{reservatie-id}")
    public ResponseEntity<ReservatieDTO> getReservatieById(
            @PathVariable("user-id") Long userId,
            @PathVariable("reservatie-id") Long id){
        ReservatieDTO reservatieDTO = reservatieService.getReservatieByUserIdAndReservatieId(userId,id);
        return ResponseEntity.ok(reservatieDTO);
    }

    @Operation(summary = "createReservatie")
    @PostMapping("/{user-id}/reservations")
    public ResponseEntity<ReservatieDTO> createReservatie(
            @PathVariable("user-id")Long id,
            @RequestBody Reservatie reservatie) {
        UserDTO userDTO = userService.getUserById(id);
        User user = userService.convertToEntity(userDTO);
        reservatie.setUser(user);
        ReservatieDTO newReservatie = reservatieService.addReservatie(reservatie);
        return ResponseEntity.status(HttpStatus.CREATED).body((newReservatie));
    }

    @Operation(summary = "addLokaalToReservatie")
    @PutMapping("{user-id}/reservations/{reservatie-id}/rooms/{room-id}")
    public ResponseEntity<ReservatieDTO> addLokaalToReservatie(
            @PathVariable("user-id")Long userId,
            @PathVariable("reservatie-id")Long id,
            @PathVariable("room-id")Long roomId) {
        ReservatieDTO updateReservatie = reservatieService.putReservatieToLokaal(userId,id,roomId);
        return ResponseEntity.ok(updateReservatie);
    }

    @Operation(summary = "deleteReservatie")
    @DeleteMapping("/{user-id}/reservations/{reservatie-id}")
    public ResponseEntity<String> deleteReservatie(@PathVariable("reservatie-id")Long id) {
        reservatieService.deleteReservatie(id);
        return ResponseEntity.ok("Succesvol verwijderd!");

    }
}
