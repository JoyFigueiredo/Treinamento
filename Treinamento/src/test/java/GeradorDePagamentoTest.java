
import java.util.Arrays;
import java.util.Calendar;
import main.Controller.CriadorDeLeilao;
import main.Controller.GeradorDePagamento;
import main.DAO.RepositorioDeLeiloes;
import main.DAO.RepositorioDePagamentos;
import main.MODEL.Avaliador;
import main.MODEL.Leilao;
import main.MODEL.Pagamento;
import main.MODEL.Relogio;
import main.MODEL.Usuario;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Joice
 */
public class GeradorDePagamentoTest {

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        RepositorioDeLeiloes leiloes = mock(RepositorioDeLeiloes.class);
        RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
        Avaliador avaliador = mock(Avaliador.class);

        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation")
                .lance(new Usuario("Jose da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(leiloes.encerrados())
                .thenReturn(Arrays.asList(leilao));
        when(avaliador.getMaiorLance())
                .thenReturn(2500.0);

        GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador);
        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);

        verify(pagamentos).salva(argumento.capture());

        Pagamento pagamentoGerado = argumento.getValue();
        assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);

    }

    @Test
    public void deveEmpurrarParaOProximoDiaUtil() {
        RepositorioDeLeiloes leiloes = mock(RepositorioDeLeiloes.class);
        RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
        Relogio relogio = mock(Relogio.class);

        Calendar sabado = Calendar.getInstance();
        sabado.set(2022, Calendar.APRIL, 2);

        //ensina o mock a dizer que hoje é sabado.
        when(relogio.hoje()).thenReturn(sabado);

        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation")
                .lance(new Usuario("Jose da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(leiloes.encerrados())
                .thenReturn(Arrays.asList(leilao));

        GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);
        gerador.gera();//

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);

        verify(pagamentos).salva(argumento.capture());

        Pagamento pagamentoGerado = argumento.getValue();

        assertEquals(Calendar.MONDAY, pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
        assertEquals(4, pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));

    }
}
