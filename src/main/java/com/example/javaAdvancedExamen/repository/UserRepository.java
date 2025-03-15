package com.example.javaAdvancedExamen.repository;

import com.example.javaAdvancedExamen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmailAdres(String emailAdres);
    List<User> findByVoorNaamLikeOrAchterNaamLike(String voorNaam,String achterNaam);
}
