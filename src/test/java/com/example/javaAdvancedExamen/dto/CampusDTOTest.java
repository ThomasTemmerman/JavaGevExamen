package com.example.javaAdvancedExamen.dto;


import com.example.javaAdvancedExamen.dto.CampusDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.entity.Lokaal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CampusDTOTest {

    @Test
    public void ShouldReturnCorrectDTOWithTwoLokalen() {
        Campus campus = new Campus();
        campus.setCampusNaam("TestCampus");
        campus.setAdres("TestAdres");
        campus.setAantalParkeerPlaatsen(100);

        Lokaal lokaal1 = new Lokaal();
        lokaal1.setLokaalNaam("Lokaal1");
        lokaal1.setCampus(campus);

        Lokaal lokaal2 = new Lokaal();
        lokaal2.setLokaalNaam("Lokaal2");
        lokaal2.setCampus(campus);

        List<Lokaal> lokalen = Arrays.asList(lokaal1, lokaal2);
        campus.setLokalen(lokalen);

        CampusDTO dto = new CampusDTO(campus);
        assertEquals("TestCampus", dto.getCampusNaam());
        assertEquals(2, dto.getAantalLokalen());
        assertEquals(2, dto.getLokalen().size());

    }
}
