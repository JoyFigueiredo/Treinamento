package MODEL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

/**
 *
 * @author Joice
 */
@Entity
//@Table(name = "tbl_usuario")
public class Usuario {
    
    private int id;
    private String nome;
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
    
}
