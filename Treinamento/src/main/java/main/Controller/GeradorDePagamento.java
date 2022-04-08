package main.Controller;

import java.util.Calendar;
import java.util.List;
import main.DAO.RepositorioDeLeiloes;
import main.DAO.RepositorioDePagamentos;
import main.MODEL.Avaliador;
import main.MODEL.Leilao;
import main.MODEL.Pagamento;
import main.MODEL.Relogio;
import main.MODEL.RelogioDoSistema;

/**
 *
 * @author Joice
 */
public class GeradorDePagamento {

    private final RepositorioDePagamentos pagamentos;
    private final RepositorioDeLeiloes leiloes;
    private final Avaliador avaliador;
    private final Relogio relogio;

    public GeradorDePagamento(RepositorioDeLeiloes leiloes, RepositorioDePagamentos pagamentos,  
            Avaliador avaliador, Relogio relogio) {
        this.leiloes = leiloes;
        this.pagamentos = pagamentos;
        this.avaliador = avaliador;
        this.relogio = relogio;
    }

    public GeradorDePagamento(RepositorioDeLeiloes leiloes, 
            RepositorioDePagamentos pagamentos, Avaliador avaliador) {

        this(
                leiloes,
                pagamentos,
                avaliador,
                new RelogioDoSistema()
        );
    }
    
    private Calendar primeiroDiaUtil() {
        Calendar data = relogio.hoje();
        int diaSemana = data.get(Calendar.DAY_OF_WEEK);

        if (diaSemana == Calendar.SATURDAY) {
            data.add(Calendar.DAY_OF_MONTH, 2);
        } else if (diaSemana == Calendar.SUNDAY) {
            data.add(Calendar.DAY_OF_MONTH, 1);
        }

        return data;
    }

    public void gera() {
        List<Leilao> leiloesEncerrados = leiloes.encerrados();
        for (Leilao leilao : leiloesEncerrados) {
            avaliador.avalia(leilao);

            Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
            pagamentos.salva(novoPagamento);
        }
    }    
}
