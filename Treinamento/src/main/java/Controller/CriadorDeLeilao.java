package Controller;

import MODEL.Lance;
import MODEL.Leilao;
import MODEL.Usuario;
import java.util.Calendar;

/**
 *
 * @author Joice
 */
public class CriadorDeLeilao {

    private Leilao leilao;

    public CriadorDeLeilao() {
    }

    public CriadorDeLeilao para(String descricao) {
        this.leilao = new Leilao(descricao);
        return this;
    }

    public CriadorDeLeilao lance(Usuario usuario, double valor) {
        leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public Leilao constroi() {
        return leilao;
    }

    public CriadorDeLeilao naData(Calendar antiga) {
        this.leilao.setData(antiga);
        return this;
    }
}
