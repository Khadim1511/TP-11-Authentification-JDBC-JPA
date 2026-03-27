package ma.fstg.security.entities;

import jakarta.persistence.*;

/**
 * Entité JPA représentant un rôle (ex: ROLE_ADMIN, ROLE_USER).
 * Chaque rôle est stocké dans la table "role" en base de données.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du rôle : doit avoir le préfixe "ROLE_" (ex: ROLE_ADMIN)
    private String name;

    // ---- Constructeurs ----

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "'}";
    }
}
