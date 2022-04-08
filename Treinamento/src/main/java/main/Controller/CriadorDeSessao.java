package main.Controller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Joice
 */
public class CriadorDeSessao {

    Session session = null;

    public CriadorDeSessao() {
        session = HibernateUtil.getSession();

        try {
            session.beginTransaction();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

}
