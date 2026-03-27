package ma.fstg.security.config;

import ma.fstg.security.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration principale de Spring Security.
 * Utilise BCrypt pour encoder les mots de passe et
 * DaoAuthenticationProvider pour interroger la base via JPA.
 */
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Bean BCrypt : encodeur de mots de passe robuste.
     * Remplace {noop} utilisé dans le TP précédent.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * DaoAuthenticationProvider : fait le pont entre Spring Security
     * et notre CustomUserDetailsService.
     * Il utilise le UserDetailsService pour charger l'utilisateur
     * et BCrypt pour vérifier le mot de passe.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Configuration de la chaîne de filtres de sécurité.
     * Définit les règles d'accès, le formulaire de login et le logout.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // Pages accessibles sans authentification
                .requestMatchers("/login", "/register").permitAll()
                // Routes réservées aux ADMIN (préfixe ROLE_ géré par Spring)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Routes accessibles aux USER et ADMIN
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                // Toutes les autres routes nécessitent une authentification
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
