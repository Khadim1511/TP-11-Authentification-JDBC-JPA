package ma.fstg.security.config;

import ma.fstg.security.entities.Role;
import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.RoleRepository;
import ma.fstg.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Initialise automatiquement la base de données avec des données de test
 * au démarrage de l'application.
 *
 * Les mots de passe sont encodés avec BCrypt — jamais stockés en clair.
 */
@Component
public class DatabaseInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo,
                           UserRepository userRepo,
                           BCryptPasswordEncoder encoder) {
        return args -> {
            // Evite de dupliquer les données à chaque redémarrage
            if (roleRepo.count() > 0) return;

            // ---- Création des rôles ----
            // Spring Security attend le préfixe "ROLE_"
            Role adminRole = roleRepo.save(new Role(null, "ROLE_ADMIN"));
            Role userRole  = roleRepo.save(new Role(null, "ROLE_USER"));

            // ---- Création des utilisateurs ----
            // encoder.encode() applique BCrypt (hachage sécurisé)
            User admin = new User(null, "admin", encoder.encode("1234"), true,
                                  List.of(adminRole, userRole));
            User user  = new User(null, "user",  encoder.encode("1111"), true,
                                  List.of(userRole));

            userRepo.saveAll(List.of(admin, user));

            System.out.println("✅ Base de données initialisée avec succès !");
            System.out.println("   admin / 1234 → ROLE_ADMIN + ROLE_USER");
            System.out.println("   user  / 1111 → ROLE_USER");
        };
    }
}
