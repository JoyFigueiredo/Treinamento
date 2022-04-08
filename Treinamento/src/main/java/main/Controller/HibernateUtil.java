package main.Controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Joice
 */
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory = getSessionFactory();

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                /*
            Cria o registro. 
            Para saber mais 
            https://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/boot/registry/class-use/StandardServiceRegistry.html
                 */
                registry = new StandardServiceRegistryBuilder().configure().build();

                /*
            Cria o MetadataSources.
                 */
                MetadataSources sources = new MetadataSources(registry);

                /*
            Cria Metadata
                 */
                Metadata metadata = sources.getMetadataBuilder().build();

                /*
            Cria SessionFactory
                 */
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();;
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }

            }
        }
        return sessionFactory;
    }

    public static void fecharConexoes() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
