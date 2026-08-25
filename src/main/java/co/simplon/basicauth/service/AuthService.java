package co.simplon.basicauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.simplon.basicauth.repository.UserRepository;

@Service
//Ce service implémente UserDetailsService, l'interface utilisée par Spring Security pour charger un utilisateur à partir de son identifiant.
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepositoryInjected) {
        this.userRepository = userRepositoryInjected;
    }

    @Override
    //La méthode loadUserByUsername est appelée automatiquement lors de l'authentification. Elle récupère l'utilisateur depuis la base de données via le repository et findByUsername.
    //Puis la méthode retourne un UserDetails s'il existe, ou lève une UsernameNotFoundException si l'utilisateur est introuvable, ce qui provoque l'échec de l'authentification.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }
}