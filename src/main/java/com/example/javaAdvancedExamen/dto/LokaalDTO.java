package com.example.javaAdvancedExamen.dto;

import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.entity.Lokaal;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LokaalDTO {
    private Long id;
    private String lokaalNaam;
    private String typeLokaal;
    private int capaciteit;
    private String achterNaam;
    private int verdieping;
    private String campusNaam;
    private final int aantalReservaties;

    public LokaalDTO(Lokaal lokaal, boolean includeCampus) {
        this.id = lokaal.getId();
        this.lokaalNaam = lokaal.getLokaalNaam();
        this.typeLokaal = lokaal.getTypeLokaal();
        this.capaciteit = lokaal.getCapaciteit();
        this.achterNaam = lokaal.getAchterNaam();
        this.verdieping = lokaal.getVerdieping();
        this.aantalReservaties = lokaal.aantalReservaties();
        //voorkomt oneindige lus
        this.campusNaam = lokaal.getCampus() != null ? lokaal.getCampus().getCampusNaam() : null;
//        this.reservaties = lokaal.getReservaties().stream()
//                .map(ReservatieDTO::new).toList();
    }

    public LokaalDTO(Lokaal lokaal) {
        this(lokaal, true);
    }

    @JsonProperty("Aantal reservaties")
    public int getAantalReservaties() {
        return aantalReservaties;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampusNaam() {
        return campusNaam;
    }

    public void setCampusNaam(String campusNaam) {
        this.campusNaam = campusNaam;
    }

    public String getLokaalNaam() {
        return lokaalNaam;
    }

    public void setLokaalNaam(String lokaalNaam) {
        this.lokaalNaam = lokaalNaam;
    }

    public String getTypeLokaal() {
        return typeLokaal;
    }

    public void setTypeLokaal(String typeLokaal) {
        this.typeLokaal = typeLokaal;
    }

    public int getCapaciteit() {
        return capaciteit;
    }

    public void setCapaciteit(int capaciteit) {
        this.capaciteit = capaciteit;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public int getVerdieping() {
        return verdieping;
    }

    public void setVerdieping(int verdieping) {
        this.verdieping = verdieping;
    }

}
