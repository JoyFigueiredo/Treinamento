package main.MODEL;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joice
 */
@Table(name = "tbl_Pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idPagamento;
    @Column
    private double valor;
    @Column
    private Calendar data;

    public Pagamento(double valor, Calendar data) {
        this.valor = valor;
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public Calendar getData() {
        return data;
    }
}
