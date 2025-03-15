package com.example.javaAdvancedExamen.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservatie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_tijdstip")
    private LocalDateTime startTijdstip;
    @Column(name = "einde_tijdstip")
    private LocalDateTime eindeTijdstip;
    private String commentaar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "reservatie_lokaal", joinColumns = @JoinColumn(name = "reservatie_id"),
    inverseJoinColumns = @JoinColumn(name = "lokaal_id"))
    private Set<Lokaal> lokalen = new HashSet<>();//voorkomen van duplicaten(zelfde reservatie)

    public Reservatie(Long id, LocalDateTime startTijdstip, LocalDateTime eindeTijdstip, String commentaar, User user, Set<Lokaal> lokalen) {
        this.id = id;
        this.startTijdstip = startTijdstip;
        this.eindeTijdstip = eindeTijdstip;
        this.commentaar = commentaar;
        this.user = user;
        this.lokalen = lokalen;
    }
    public Reservatie() {

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Lokaal> getLokalen() {
        return lokalen;
    }

    public void setLokalen(Set<Lokaal> lokalen) {
        this.lokalen = lokalen;
    }
}
