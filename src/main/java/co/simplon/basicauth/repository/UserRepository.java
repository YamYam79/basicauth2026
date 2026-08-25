package co.simplon.basicauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.basicauth.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
//UserRepository contient une méthode findByUsername, qui servira plus tard à retrouver automatiquement un utilisateur à partir de son nom d'utilisateur lors de l'authentification.
    public Optional<UserEntity> findByUsername(String username);
}