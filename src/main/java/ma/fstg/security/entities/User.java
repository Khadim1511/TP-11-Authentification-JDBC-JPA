package ma.fstg.security.entities;

import jakarta.persistence.*;

import java.util.*;

/**
 * Entité JPA représentant un utilisateur de l'application.
 * Mappée sur la table "users" en base de données.
 */
@Entity
@Table(name = "users") // "user" est un mot réservé en MySQL, on utilise "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom d'utilisateur (doit être unique)
    @Column(unique = true, nullable = false)
    private String username;

    // Mot de passe encodé avec BCrypt
    @Column(nullable = false)
    private String password;

    // Permet de désactiver un compte sans le supprimer
    private boolean active;

    /**
     * Relation ManyToMany avec Role :
     * - Un utilisateur peut avoir plusieurs rôles
     * - Un rôle peut appartenir à plusieurs utilisateurs
     * - FetchType.EAGER : les rôles sont chargés immédiatement avec l'utilisateur
     *   (nécessaire pour Spring Security)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    // ---- Constructeurs ----

    public User() {}

    public User(Long id, String username, String password, boolean active, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Collection<Role> getRoles() { return roles; }
    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', active=" + active + "}";
    }
}
