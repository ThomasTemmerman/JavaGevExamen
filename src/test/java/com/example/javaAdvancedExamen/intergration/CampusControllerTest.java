package com.example.javaAdvancedExamen.intergration;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.repository.CampusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.Arrays;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CampusControllerTest {
    @Autowired
    private WebTestClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void TestCampusRepoWith_H2_DB() throws JsonProcessingException {
        Campus campus = new Campus();
        campus.setCampusNaam("CampusTest");
        campus.setAantalParkeerPlaatsen(50);
        campus.setAdres("Boekentoren 1, 9090 Gent");

        String campusJson = objectMapper.writeValueAsString(campus);


        client.post()
                .uri("/campus/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(campusJson)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.campusNaam").isEqualTo("CampusTest")
                .jsonPath("$.adres").isEqualTo("Boekentoren 1, 9090 Gent")
                .jsonPath("$.aantalParkeerPlaatsen").isEqualTo(50);
    }
}
