package com.example.javaAdvancedExamen.dto;

import com.example.javaAdvancedExamen.entity.Reservatie;
import com.example.javaAdvancedExamen.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Long id;
    private String voorNaam;
    private String achterNaam;
    private LocalDate geboortedatum;
    private String emailAdres;
    private final int aantalReservaties;
    //private List<ReservatieDTO> reservaties;

    public UserDTO(User user) {
        this.id = user.getId();
        this.emailAdres = user.getEmailAdres();
        this.voorNaam = user.getVoorNaam();
        this.achterNaam = user.getAchterNaam();
        this.geboortedatum = user.getGeboortedatum();
        this.aantalReservaties = user.aantalReservaties();
//        this.reservaties = user.getReservaties() != null
//                ? user.getReservaties().stream()
//                        .map(ReservatieDTO::new)
//                        .toList()
//                : new ArrayList<>();
    }

    @JsonProperty("aantal reservaties")//voor JSON
    public int getAantalReservaties() {
        return aantalReservaties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate   geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }


//    public List<ReservatieDTO> getReservaties() {
//        return reservaties;
//    }
//
//    public void setReservaties(List<ReservatieDTO> reservaties) {
//        this.reservaties = reservaties;
//    }
}
