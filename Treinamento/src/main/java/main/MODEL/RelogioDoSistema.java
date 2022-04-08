package main.MODEL;

import java.util.Calendar;

/**
 *
 * @author Joice
 */
public class RelogioDoSistema implements Relogio {

    @Override
    public Calendar hoje() {
        return Calendar.getInstance();
    }
}
