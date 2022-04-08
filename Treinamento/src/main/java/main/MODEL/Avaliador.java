package main.MODEL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joice
 */
@Entity
@Table(name = "tbl_Avaliador")
public class Avaliador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idAvaliador;
    @Column
    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    @Column
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    @Column
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {
// lançando a exceção
        if (leilao.getLances().size() == 0) {
            throw new RuntimeException(
                    "Não é possível avaliar um leilão sem lances"
            );
        }
        for (Lance lance : leilao.getLances()) {
            if (lance.getValor() > maiorDeTodos) {
                maiorDeTodos = lance.getValor();
            }
            if (lance.getValor() < menorDeTodos) {
                menorDeTodos = lance.getValor();
            }
        }
        pegaOsMaioresNo(leilao);
    }

    private void pegaOsMaioresNo(Leilao leilao) {
        maiores = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance>() {
            public int compare(Lance o1, Lance o2) {
                if (o1.getValor() < o2.getValor()) {
                    return 1;
                }
                if (o1.getValor() > o2.getValor()) {
                    return -1;
                }
                return 0;
            }
        });
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());

    }

    public List<Lance> getTresMaiores() {
        return this.maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }
}
