package com.example.javaAdvancedExamen.dto;

import com.example.javaAdvancedExamen.entity.Campus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CampusDTO {
    private String campusNaam;
    private String adres;
    private int aantalParkeerPlaatsen;
    private final int aantalLokalen;
    private List<LokaalDTO> lokalen;

    public CampusDTO(Campus campus) {
        this.campusNaam = campus.getCampusNaam();
        this.adres = campus.getAdres();
        this.aantalParkeerPlaatsen = campus.getAantalParkeerPlaatsen();
        this.aantalLokalen = campus.getAantalLokalen();
        this.lokalen = campus.getLokalen() != null ?//voorkomt oneindige lus
                campus.getLokalen().stream()
                        .map(lokaal -> new LokaalDTO(lokaal, false))
                        .toList()
                : new ArrayList<>();
    }
    @JsonProperty("aantal lokalen")//voor JSON
    public int getAantalLokalen() {
        return aantalLokalen;
    }

    public List<LokaalDTO> getLokalen() {
        return lokalen;
    }

    public void setLokalen(List<LokaalDTO> lokalen) {
        this.lokalen = lokalen;
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

    @Override
    public String toString() {
        return "Campus naam: " + campusNaam +" / Adres: " + adres + " / Aantal parkeer plaatsen: " + aantalParkeerPlaatsen;
    }
}
