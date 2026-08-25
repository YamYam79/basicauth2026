package co.simplon.basicauth.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.simplon.basicauth.entity.RoleEntity;
import co.simplon.basicauth.entity.TodoEntity;
import co.simplon.basicauth.repository.RoleRepository;
import co.simplon.basicauth.repository.TodoRepository;

//DataInitializer est la classe qui sert à initiliaser des données au démarrage de l'application
//Cette classe est détectée automatiquement par Spring grâce à @Component
@Component
// Comme DataInitializer implémente CommandLineRunner, Spring éxecutera sa
// méthode "run(...)"
// Une fois l'application démarrée, c'est souvent utilisé pour :
// -créer des données de test ;
// -alimenter une base vide ;
// -créer des rôles par défaut ;
// -créer un administrateur initial.

// Pour pouvoir attribuer un rôle à un utilisateur, il faut souvent que ce rôle
// existe déjà en base. C'est précisément ce que fait DataInitializer
public class DataInitializer implements CommandLineRunner {

    private final TodoRepository todoRepository;
    private final RoleRepository roleRepository;

    // Injection de dépendances :
    // Spring fournit automatiquement les objets :TodoRepository et RoleRepository à
    // la création du composant.
    // C'est ce qu'on appelle l'injection par constructeur.
    public DataInitializer(
            TodoRepository todoRepositoryInjected,
            RoleRepository roleRepositoryInjected) {
        this.todoRepository = todoRepositoryInjected;
        this.roleRepository = roleRepositoryInjected;
    }

    // La méthode run() => Cette méthode est appelée automatiquement au démarrage.
    @Override
    public void run(String... args) throws Exception {
        this.todoRepository.save(new TodoEntity("Clone the project", true));
        this.todoRepository.save(new TodoEntity("Test the API", true));
        this.todoRepository.save(new TodoEntity("Add basic authentication"));

        //Création du rôle USER => Création d'un objet vide => sur lequel on affecte "ROLE_USER"
        RoleEntity roleUser = new RoleEntity();
        roleUser.setAuthority("ROLE_USER");
        //Insertion en base ave roleRepository.save, ce qui crée la ligne "ROLE_USER"
        roleRepository.save(roleUser);

        //Idem, pour le rôle Admin
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setAuthority("ROLE_ADMIN");
        roleRepository.save(roleAdmin);
    }
}
// Résumé de cette classe "DataInitializer.java" :
// Cette classe :
// S'exécute automatiquement au démarrage grâce à CommandLineRunner.
// Crée 3 tâches (TodoEntity) dans la base.
// Crée les rôles : ROLE_USER et ROLE_ADMIN
// Les sauvegarde avec les repositories.

// => C'est donc une classe de chargement de données initiales (seed data)
// utilisée principalement pour les tests et le développement.

// Schéma résumé :
// Application démarre
//      ↓
// Spring crée les beans
//      ↓
// Spring exécute run()
//      ↓
// Les données sont insérées