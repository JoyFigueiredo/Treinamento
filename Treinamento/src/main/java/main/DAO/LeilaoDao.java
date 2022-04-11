package main.DAO;

import main.MODEL.Leilao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 *
 * @author Joice
 */
public class LeilaoDao {

    private static List<Leilao> leiloes = new ArrayList<Leilao>();
    private final Session session;

    public LeilaoDao(Session session) {
        this.session = session;
    }

    public void salva(Leilao leilao) {
        leiloes.add(leilao);
    }

    public List<Leilao> encerrados() {
        List<Leilao> filtrados = new ArrayList<Leilao>();
        for (Leilao leilao : leiloes) {
            if (leilao.isEncerrado()) {
                filtrados.add(leilao);
            }
        }
        return filtrados;
    }

    public List<Leilao> correntes() {
        List<Leilao> filtrados = new ArrayList<Leilao>();
        for (Leilao leilao : leiloes) {
            if (!leilao.isEncerrado()) {
                filtrados.add(leilao);
            }
        }
        return filtrados;
    }

    public void atualiza(Leilao leilao) {
        /* faz nada! */ }

    public Long total() {
        return (Long) session.createQuery("select count(l) from" + "Leilao l where l.encerrado = false").uniqueResult();
    }
    
    public List<Leilao> porPeriodo(Calendar inicio, Calendar fim){
        return session.createQuery("from Leilao l where l.dataAbertura" + "between :inicio and :fim and lencerrado = false")
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .list();
    }
    
    
}
