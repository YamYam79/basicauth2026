package co.simplon.basicauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import co.simplon.basicauth.service.AuthService;

@Configuration
//Pour que les @PreAuthorize soient prises en compte, il faut ajouter l'annotation @EnableMethodSecurity à cette classe SecurityConfig :
@EnableMethodSecurity
public class SecurityConfig {

private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthService authServiceInjected) {
        this.userDetailsService = authServiceInjected;
    }

    @Bean
    //La configuration ajoute un authenticationManager, qui indique à Spring Security quel service utiliser pour :
    // -charger un utilisateur à partir de son identifiant
    // -charger un passwordEncoder, chargé d'encoder les mots de passe de manière sécurisée, ici avec l'algorithme BCrypt.
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService);
        return authManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour cette application
                .authorizeHttpRequests((auth) -> auth
                 // Accessible sans authentification
                        .requestMatchers("/auth/register").permitAll()
                //"authenticated", permet l'authentification pour tout le reste
                //SecurityConfig configure une application sans session, impose une authentification pour toutes les requêtes et active explicitement l'authentification basique.
                        .anyRequest().authenticated())
                        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                //Remarque :
               // Après redémarrage du projet, toutes les routes sont protégées. Si une erreur 401 est retournée, c'est qu aucun utilisateur n'existe encore et aucun mécanisme de création n'est implémenté à ce stade.
                .build();
    }
}
