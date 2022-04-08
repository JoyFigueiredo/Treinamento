package main.MODEL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joice
 */
//@Entity
//@Table(name = "tbl_usuario")
public class Usuario {
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private int idUsuario;
//    @Column
    private String nome;
//    @Column
    private String email;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
/*
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
 */   
}
