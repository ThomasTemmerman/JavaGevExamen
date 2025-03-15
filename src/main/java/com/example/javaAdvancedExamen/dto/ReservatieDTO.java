package com.example.javaAdvancedExamen.dto;

import com.example.javaAdvancedExamen.entity.Reservatie;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ReservatieDTO {
    private Long id;
    private LocalDateTime startTijdstip;
    private LocalDateTime eindeTijdstip;
    private String commentaar;
    private List<LokaalDTO> lokalen;

    public ReservatieDTO(Reservatie reservatie) {//bug = oneindige lus bij ophalen van user → User verwijderen
        this.id = reservatie.getId();
        this.startTijdstip = reservatie.getStartTijdstip();
        this.eindeTijdstip = reservatie.getEindeTijdstip();
        this.commentaar = reservatie.getCommentaar();
        this.lokalen = reservatie.getLokalen().stream()
                .map(LokaalDTO::new)
                .toList();
    }
    @JsonProperty("Aantal lokalen")
    public int getAantalLokalen() {
        return lokalen.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTijdstip() {
        return startTijdstip;
    }

    public void setStartTijdstip(LocalDateTime startTijdstip) {
        this.startTijdstip = startTijdstip;
    }

    public LocalDateTime getEindeTijdstip() {
        return eindeTijdstip;
    }

    public void setEindeTijdstip(LocalDateTime eindeTijdstip) {
        this.eindeTijdstip = eindeTijdstip;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public List<LokaalDTO> getLokalen() {
        return lokalen;
    }

    public void setLokalen(List<LokaalDTO> lokalen) {
        this.lokalen = lokalen;
    }
}
