package Controller;

import DAO.LeilaoDao;
import MODEL.Carteiro;
import MODEL.Leilao;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Joice
 */
public class EncerradorDeLeilao {

    private int total = 0;
    private final RepositorioDeLeiloes dao;
    private Carteiro carteiro;

    public EncerradorDeLeilao(RepositorioDeLeiloes dao) {
        this.dao = dao;
    }

    public EncerradorDeLeilao(
            RepositorioDeLeiloes dao,
            Carteiro carteiro) {
        this.dao = dao;
// guardamos o carteiro como atributo da classe
        this.carteiro = carteiro;
    }

    public void encerra() {
        List<Leilao> todosLeiloesCorrentes = dao.correntes();
        for (Leilao leilao : todosLeiloesCorrentes) {
            try {
                if (comecouSemanaPassada(leilao)) {
                    leilao.encerra();
                    total++;
                    dao.atualiza(leilao);
                    carteiro.envia(leilao);
                }
            } catch (Exception e) {
// salvo a exceção no sistema de logs
// e o loop continua!
            }
        }
    }

    private boolean comecouSemanaPassada(Leilao leilao) {
        return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
    }

    private int diasEntre(Calendar inicio, Calendar fim) {
        Calendar data = (Calendar) inicio.clone();
        int diasNoIntervalo = 0;
        while (data.before(fim)) {
            data.add(Calendar.DAY_OF_MONTH, 1);
            diasNoIntervalo++;
        }
        return diasNoIntervalo;
    }

    public int getTotalEncerrados() {
        return total;
    }
}
