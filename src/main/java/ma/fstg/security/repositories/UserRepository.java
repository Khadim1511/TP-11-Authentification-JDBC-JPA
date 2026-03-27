package ma.fstg.security.repositories;

import ma.fstg.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository JPA pour l'entité User.
 * JpaRepository fournit déjà : findAll(), save(), deleteById(), findById()...
 * On ajoute uniquement la méthode nécessaire pour Spring Security.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * Spring Data génère automatiquement la requête SQL correspondante.
     */
    Optional<User> findByUsername(String username);
}
