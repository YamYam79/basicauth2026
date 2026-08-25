package co.simplon.basicauth.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.basicauth.entity.UserEntity;
import co.simplon.basicauth.repository.UserRepository;

//Pour permettre à un utilisateur de créer un compte, il faut ajouter une route d'API /auth/register chargée :
//-d'encoder le mot de passe,
//-d'attribuer un rôle à l'utilisateur,
//-d'enregistrer l'utilisateur en base de données.

@RestController
@RequestMapping("auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(
            PasswordEncoder passwordEncoderInjected,
            UserRepository userRepositoryInjected) {
        this.passwordEncoder = passwordEncoderInjected;
        this.userRepository = userRepositoryInjected;
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}