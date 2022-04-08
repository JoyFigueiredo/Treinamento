package main.DAO;

import main.MODEL.Usuario;
import org.hibernate.Session;

/**
 *
 * @author Joice
 */
public class UsuarioDAO {

    private final Session session;

    public UsuarioDAO(Session session) {
        this.session = session;
    }

    public Usuario porId(int id) {
        return (Usuario) session.load(Usuario.class, id);
    }

    public Usuario porNomeEEmail(String nome, String email) {
        return (Usuario) session.createQuery("from Usuario u where u.nome = :nome and u.email = :email")
                .setParameter("nome", nome)
                .setParameter("email", email)
                .uniqueResult();
    }

    public void salvar(Usuario usuario) {
        session.save(usuario);
    }
}
