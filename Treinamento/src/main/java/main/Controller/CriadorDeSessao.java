package main.Controller;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mockito.Mockito;

/**
 *
 * @author Joice
 */
public class CriadorDeSessao {

    private Session session;
    private Transaction trans;

    public CriadorDeSessao() {

        try {
            /*
            Session session = Mockito.mock(Session.class);
            Query query = Mockito.mock(Query.class);
*/
            //session = HibernateUtil.getFactory().getCurrentSession();
            session = HibernateUtil.getSessionFactory().openSession();
            trans = session.beginTransaction();

            session.save(this);

            trans.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

}
