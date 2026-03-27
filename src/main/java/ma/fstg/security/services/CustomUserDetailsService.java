package ma.fstg.security.services;

import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service personnalisé qui indique à Spring Security comment charger
 * un utilisateur depuis la base de données lors du login.
 *
 * Il implémente UserDetailsService, l'interface que Spring Security
 * utilise pour récupérer un utilisateur par son nom d'utilisateur.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Méthode appelée automatiquement par Spring Security lors d'une tentative de login.
     * Elle charge l'utilisateur depuis la BDD et le convertit en objet UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche l'utilisateur en base, lève une exception si non trouvé
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur non trouvé : " + username));

        // Convertit les rôles de l'entité en GrantedAuthority pour Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),   // compte actif
                true,              // compte non expiré
                true,              // credentials non expirés
                true,              // compte non bloqué
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
