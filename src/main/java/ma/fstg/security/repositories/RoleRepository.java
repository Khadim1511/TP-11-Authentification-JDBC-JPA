package ma.fstg.security.repositories;

import ma.fstg.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité Role.
 * Fournit les opérations CRUD de base + la recherche par nom.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Recherche un rôle par son nom (ex: "ROLE_ADMIN").
     */
    Role findByName(String name);
}
