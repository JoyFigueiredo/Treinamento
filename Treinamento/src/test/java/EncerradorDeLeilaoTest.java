
import main.Controller.CriadorDeLeilao;
import main.Controller.EncerradorDeLeilao;
import main.DAO.RepositorioDeLeiloes;
import main.MODEL.Leilao;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import main.MODEL.Carteiro;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Joice
 */
public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);
        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);
        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());

        assertEquals(2, encerrador.getTotalEncerrados());

    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);
        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        RepositorioDeLeiloes daoFalso
                = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes())
                .thenReturn(Arrays.asList(leilao1));
        EncerradorDeLeilao encerrador
                = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();
// verificando que o método atualiza foi realmente invocado!
        verify(daoFalso).atualiza(leilao1);
    }

    @Test
    public void deveContinuarAExecucaoMesmoQuandoDaoFalha() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);
        Leilao leilao1 = new CriadorDeLeilao()
                .para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao()
                .para("Geladeira")
                .naData(antiga).constroi();
        RepositorioDeLeiloes daoFalso
                = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes())
                .thenReturn(Arrays.asList(leilao1, leilao2));

        doThrow(new RuntimeException()).when(daoFalso)
                .atualiza(leilao1);

        Carteiro carteiroFalso
                = mock(Carteiro.class);
        EncerradorDeLeilao encerrador
                = new EncerradorDeLeilao(daoFalso, carteiroFalso);

        encerrador.encerra();
        verify(daoFalso).atualiza(leilao2);
        verify(carteiroFalso).envia(leilao2);

    }
}
