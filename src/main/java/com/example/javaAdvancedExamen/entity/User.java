package com.example.javaAdvancedExamen.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String voorNaam;
    private String achterNaam;
    private LocalDate geboortedatum;
    @Column(name = "email_adres")
    private String emailAdres;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reservatie> reservaties = new ArrayList<>();

    public User() {

    }

    public User(Long id, String voorNaam, String achterNaam, String emailAdres,LocalDate geboortedatum) {
        this.id = id;
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
        this.emailAdres = emailAdres;
        this.geboortedatum = geboortedatum;
    }
    public List<Reservatie> getReservaties() {
        return reservaties;
    }
    public int aantalReservaties() {
        return reservaties.size();
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }
}
