package com.example.javaAdvancedExamen.service;

import com.example.javaAdvancedExamen.dto.UserDTO;
import com.example.javaAdvancedExamen.entity.User;
import com.example.javaAdvancedExamen.exceptions.AlreadyExistsException;
import com.example.javaAdvancedExamen.exceptions.NotFoundEntityException;
import com.example.javaAdvancedExamen.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Gaat een nieuwe user toevoegen
     * @param user body van user
     * @return gaat de user aanmaken
     */
    public UserDTO addUser(User user) {
            if (repo.existsByEmailAdres(user.getEmailAdres())) {
                throw new AlreadyExistsException("Deze user met het e-mailadres: " + user.getEmailAdres() + " bestaat al");
            }

        return new UserDTO(repo.save(user));
    }

    /**
     * Gaat een user wijzigen
     * @param id de PK van een user
     * @param user body van user
     * @return gaat de wijzigingen toepassen
     */
    public UserDTO editUser(Long id, User user) {
        User foundUser = repo.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("User werd niet gevonden"));

        if (!id.equals(user.getId()) && repo.existsByEmailAdres(user.getEmailAdres())) {
            throw  new AlreadyExistsException("Nieuwe user met email: " + user.getEmailAdres() + " bestaat al");
        }
        foundUser.setVoorNaam(user.getVoorNaam());
        foundUser.setAchterNaam(user.getAchterNaam());
        foundUser.setGeboortedatum(user.getGeboortedatum());
        //reservatie?

        return new UserDTO(repo.save(foundUser));
    }

    /**
     * Gaat een user verwijderen
     * @param id de PK van een user
     */
    public void deleteUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("User werd niet gevonden"));
        repo.delete(user);
    }

    /**
     * Gaat alle user weergeven
     * @return toont alle user in een lijst weer
     */
    public List<UserDTO> getAllUsers() {
        try {
            return repo.findAll().stream()
                    .map(UserDTO::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Fout bij het ophalen van users: " + e.getMessage());
        }
    }

    /**
     * Gaat een bepaalde user tonen
     * @param id de PK van een user
     * @return toont de user a.d.h.v van de id
     */
    public UserDTO getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("User werd niet gevonden"));

        return new UserDTO(user);
    }

    /**
     * Gaat een user opzoeken a.d.h.v voornaam of achternaam, maakt gebruik van wildcard
     * @param naam ingegeven naam
     * @return gaat de naam tonen
     */
    public List<UserDTO> searchUserByName(String naam) {

        if (naam.isEmpty()){
            throw new NullPointerException("Gelieve een naam in te geven");
        }

        if (naam.length() < 2) {
            throw new IllegalArgumentException("Naam moet minstens 2 tekens lang zijn");
        }

        String searchName = "%" + naam + "%";
        return repo.findByVoorNaamLikeOrAchterNaamLike(searchName,searchName)
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setGeboortedatum(userDTO.getGeboortedatum());
        user.setVoorNaam(userDTO.getVoorNaam());
        user.setAchterNaam(userDTO.getAchterNaam());
        user.setEmailAdres(userDTO.getEmailAdres());
        return user;
    }

}
