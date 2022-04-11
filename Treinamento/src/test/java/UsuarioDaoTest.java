
import main.Controller.CriadorDeSessao;
import main.DAO.UsuarioDAO;
import main.MODEL.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Joice
 */
public class UsuarioDaoTest {
    
    private Session session;
    private UsuarioDAO usuarioDao;
    private Query query;
    
    @Before
    public void antes(){
        //criamos a sessao e a passamos para o dao
        session = new CriadorDeSessao().getSession();
        query = Mockito.mock(Query.class);
        usuarioDao = new UsuarioDAO(session);
    }
    
    @After 
    public void depois(){
        //fechamos a sessao
        session.close();
    }
    
    @Test
    public void deveEncontrarPeloNomeEEmailMockado(){
       session = Mockito.mock(Session.class);
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
        Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
        usuarioDao.salvar(novoUsuario);
        
        Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("João da Silva", "joao@dasilva.com.br");
        
        assertEquals("Joao da Silva", usuarioDoBanco.getNome());
        assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());
        
        session.close();
    }
    
    @Test 
    public void deveRetornaNuloSeNaoEncontrarUsuario(){
        Usuario usuarioDobanco = usuarioDao
                .porNomeEEmail("João Joaquim", "joao@joaquim.com.br");
        assertNull(usuarioDobanco);
    }
    
    @Test 
    public void deveDeletarUmUsuario(){
        Usuario usuario = new Usuario("Mauricio Aniche", "Mauricio@aniche.com.br");
        
        usuarioDao.salvar(usuario);
        usuarioDao.deletar(usuario);
        
        //envia tudo para o banco de dados
        session.flush();
        Usuario usuarioNoBanco = usuarioDao.porNomeEEmail("Mauricio Aniche", "Mauricio@aniche.com.br");
        assertNull(usuarioNoBanco);
    }
}
