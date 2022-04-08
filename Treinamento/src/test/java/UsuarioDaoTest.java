
import main.Controller.CriadorDeSessao;
import main.DAO.UsuarioDAO;
import main.MODEL.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Joice
 */
public class UsuarioDaoTest {
    
    @Test
    public void deveEncontrarPeloNomeEEmailMockado(){
        Session session = Mockito.mock(Session.class);
        Query query = Mockito.mock(Query.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO(session);
        
        Usuario usuario = new Usuario("João da Silva", "joao@dasilva.com.br");
        
        String sql = "from Usuario u where u.nome = :nome and u.email = :email";
        
        Mockito.when(session.createQuery(sql)).thenReturn(query);
        Mockito.when(query.uniqueResult()).thenReturn(usuario);
        Mockito.when(query.setParameter("nome", "João da Silva")).thenReturn(query);
        Mockito.when(query.setParameter("email", "joao@dasilva.com.br")).thenReturn(query);
        
        Usuario usuarioDoBanco = usuarioDAO.porNomeEEmail("João da Silva", "joao@dasilva.com.br");
        
        assertEquals(usuario.getNome(), usuarioDoBanco.getNome());
        assertEquals(usuario.getEmail(), usuarioDoBanco.getEmail());
        
        session.close();
    }
    
    @Test
    public void deveEncontrarPeloNomeEEmail(){
        Session session = new CriadorDeSessao().getSession();
        UsuarioDAO usuarioDAO = new UsuarioDAO(session);
        
        Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
        usuarioDAO.salvar(novoUsuario);
        
        Usuario usuarioDoBanco = usuarioDAO.porNomeEEmail("João da Silva", "joao@dasilva.com.br");
        
        assertEquals("Joao da Silva", usuarioDoBanco.getNome());
        assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());
        
        session.close();
    }
}
