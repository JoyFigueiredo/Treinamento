package main.MODEL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
@Table(name = "tbl_Leilao")
public class Leilao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idLeilao;
    @Column
    private String descricao;
    @Column
    private List<Lance> lances;
    @Column
    private Calendar data;
    @Column
    private boolean encerrado; 
    
    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<Lance>();
    }

    public Leilao(String descricao, double d, Usuario usuario, boolean b) {
        this.descricao = descricao;
        this.lances.get(idLeilao).setValor(d);
        this.lances.get(idLeilao).setUsuario(usuario);
        this.encerrado = b;
    }

    public void propoe(Lance lance) {
        if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
            lances.add(lance);
        }
    }

    private boolean podeDarLance(Usuario usuario) {
        return !ultimoLanceDado().getUsuario().equals(usuario)
                && qtdDelancesDo(usuario) < 5;
    }

    private int qtdDelancesDo(Usuario usuario) {
        int total = 0;
        for (Lance lance : lances) {
            if (lance.getUsuario().equals(usuario)) {
                total++;
            }
        }
        return total;
    }
    
    public Usuario getNome(){
        return lances.get(idLeilao).getUsuario();
    }

    private Lance ultimoLanceDado() {
        return lances.get(lances.size() - 1);
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> getLances() {
        return Collections.unmodifiableList(lances);
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public void encerra() {
        this.encerrado = true;
    }

    public int getIdLeilao() {
        return idLeilao;
    }

    public void setIdLeilao(int idLeilao) {
        this.idLeilao = idLeilao;
    }
}
