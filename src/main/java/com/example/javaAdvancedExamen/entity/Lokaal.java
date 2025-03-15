package com.example.javaAdvancedExamen.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Lokaal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lokaalNaam;
    private String typeLokaal;
    private int capaciteit;
    private String voorNaam;
    private String achterNaam;
    private int verdieping;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_naam")
    private Campus campus;
    @ManyToMany(mappedBy = "lokalen")
    private Set<Reservatie> reservaties = new HashSet<>();//voorkomen van duplicaten(geen overlapping)

    public Lokaal() {

    }

    public Lokaal(Long id,String lokaalNaam,String typeLokaal, int capaciteit, String voorNaam, String achterNaam, int verdieping, Campus campus) {
        this.id = id;
        this.lokaalNaam = lokaalNaam;
        this.typeLokaal = typeLokaal;
        this.capaciteit = capaciteit;
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
        this.verdieping = verdieping;
        this.campus = campus;
    }

    public Set<Reservatie> getReservaties() {
        return reservaties;
    }

    public void setReservaties(Set<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }

    public int aantalReservaties() {
        return reservaties.size();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeLokaal() {
        return typeLokaal;
    }

    public String getLokaalNaam() {
        return lokaalNaam;
    }

    public void setLokaalNaam(String lokaalNaam) {
        this.lokaalNaam = lokaalNaam;
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

    public int getVerdieping() {
        return verdieping;
    }

    public void setVerdieping(int verdieping) {
        this.verdieping = verdieping;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Lokaal(String typeLokaal, int capaciteit, String voorNaam, String achterNaam, int verdieping, Campus campus) {
        this.typeLokaal = typeLokaal;
        this.capaciteit = capaciteit;
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
        this.verdieping = verdieping;
        this.campus = campus;
    }
}
