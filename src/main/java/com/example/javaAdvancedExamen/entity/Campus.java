package com.example.javaAdvancedExamen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Campus {
    @Id
    private String campusNaam;
    private String adres;
    @Column(name = "parkeer_plaatsen")
    private int aantalParkeerPlaatsen;
    @OneToMany(mappedBy = "campus", fetch = FetchType.LAZY)
    private List<Lokaal> lokalen = new ArrayList<>();

    public int getAantalLokalen() {
        return lokalen.size();
    }

    public Campus(String campusNaam, String adres, int aantalParkeerPlaatsen) {
        this.campusNaam = campusNaam;
        this.adres = adres;
        this.aantalParkeerPlaatsen = aantalParkeerPlaatsen;
    }
    public Campus() {

    }
    public String getCampusNaam() {
        return campusNaam;
    }

    public void setCampusNaam(String campusNaam) {
        this.campusNaam = campusNaam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getAantalParkeerPlaatsen() {
        return aantalParkeerPlaatsen;
    }

    public void setAantalParkeerPlaatsen(int aantalParkeerPlaatsen) {
        this.aantalParkeerPlaatsen = aantalParkeerPlaatsen;
    }

    public List<Lokaal> getLokalen() {
        return lokalen;
    }

    public void setLokalen(List<Lokaal> lokalen) {
        this.lokalen = lokalen;
    }
}