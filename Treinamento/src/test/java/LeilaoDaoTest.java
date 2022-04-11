
import java.util.Calendar;
import java.util.List;
import main.Controller.CriadorDeSessao;
import main.DAO.LeilaoDao;
import main.DAO.UsuarioDAO;
import main.MODEL.Leilao;
import main.MODEL.Usuario;
import org.hibernate.Session;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joice
 */
public class LeilaoDaoTest {

    private Session session;
    private LeilaoDao leilaoDao;
    private UsuarioDAO usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        leilaoDao = new LeilaoDao(session);
        usuarioDao = new UsuarioDAO(session);

        session.beginTransaction();
    }

    @After
    public void depois() {
        //garante que o banco sempre esteja limpo
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveContarLeiloesNaoEncerrados() {
        //criamos um usuario
        Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        //criamos os dois leiloes 
        Leilao ativo = new Leilao("Geladeira", 1500.0, mauricio, false);
        Leilao encerrado = new Leilao("XBox", 700.0, mauricio, false);

        encerrado.encerra();

        //persistimos todos no banco 
        usuarioDao.salvar(mauricio);
        leilaoDao.salva(ativo);
        leilaoDao.salva(encerrado);

        //invocamos a ação que queremos testar
        //pedimos o total parao DAO
        long total = leilaoDao.total();

        assertEquals(1L, total);

    }

    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
        //criamos as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar dataDoLeilao2 = Calendar.getInstance();
        dataDoLeilao2.add(Calendar.DAY_OF_MONTH, -20);

        Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        //criamos os leilões cada um com uma data
        Leilao leilao1 = new Leilao("Xbox", 700.0, mauricio, false);
        leilao1.setData(dataDoLeilao1);
        Leilao leilao2 = new Leilao("Geladeira", 1700.0, mauricio, false);
        leilao2.setData(dataDoLeilao2);

        //invocando o metodo para testar
        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        //garantindo que a query funcionou
        assertEquals(1, leiloes.size());
        assertEquals("XBox", leiloes.get(0).getNome());
    }

    @Test
    public void naoDeveTrazerLeiloesNaoEncerradosNoPeriodo() {
        //criamos as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);

        Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        //criamos os leilões cada um com uma data
        Leilao leilao1 = new Leilao("Xbox", 700.0, mauricio, false);
        leilao1.setData(dataDoLeilao1);
        leilao1.encerra();

        //persistimos o metodo para testar
        usuarioDao.salvar(mauricio);
        leilaoDao.salva(leilao1);

        //invocando o metodo para testar
        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        //garantindo que a query funcionou
        assertEquals(0, leiloes.size());
    }
}
