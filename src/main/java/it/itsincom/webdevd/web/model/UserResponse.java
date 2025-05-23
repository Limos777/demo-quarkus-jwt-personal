package it.itsincom.webdevd.web.model;

import it.itsincom.webdevd.persistence.model.UserRole;

public class UserResponse {
    private long id;
    private String username;
    private String role;
    private String nome;
    private String cognome;
    private String indirizzo;




    public UserResponse(long id, String username, String role, String nome, String cognome, String indirizzo) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
