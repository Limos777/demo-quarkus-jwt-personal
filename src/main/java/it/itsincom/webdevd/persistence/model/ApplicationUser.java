package it.itsincom.webdevd.persistence.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

@Entity
@Table(name = "application_user")
@UserDefinition
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Username
    private String username;

    @Column(name = "password", nullable = false)
    @Password
    private String password;

    @Column(name = "role", nullable = false)
    @Roles
    private String role;

    @Column(name = "Nome", length = 255)
    private String nome;

    @Column(name = "Cognome", length = 255)
    private String cognome;

    @Column(name = "Indirizzo", length = 255)
    private String indirizzo;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, UserRole role, String nome, String cognome, String indirizzo) {
        this.username = username;
        this.password = password;
        this.role = role.name();
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

}
