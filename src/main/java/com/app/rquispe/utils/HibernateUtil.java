package com.app.rquispe.utils;

import com.app.rquispe.domain.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Created by ruben on 8/14/15.
 */
public class HibernateUtil {

    public static Configuration getConfiguration() {
        return new Configuration().configure();
        /*Configuration cfg = new Configuration()
         *.addResource("Item.hbm.xml")
         *.addResource("Bid.hbm.xml");
         *
         * An alternative way is to specify the mapped class
         * and allow Hibernate to find the mapping document for you:
         * Hibernate will then search for mapping files named
         * /com/app/rquispe/domain/Person.hbm.xml and /com/app/rquispe/domain/Event.hbm.xml in the classpath
         * Configuration cfg = new Configuration()
            .addClass(com.app.rquispe.domain.Person.class)
            .addClass(com.app.rquispe.domain.Event.class);

         also allows you to specify configuration properties.
         Configuration cfg = new Configuration()
        .addClass(com.app.rquispe.domain.Person.class)
        .addClass(com.app.rquispe.domain.Event.class);
        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
        .setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/test")
        .setProperty("hibernate.order_updates", "true");

        This is not the only way to pass configuration properties to Hibernate.
         Some alternative options include:
        1. Pass an instance of java.util.Properties to Configuration.setProperties().
        2. Place a file named hibernate.properties in a root directory of the classpath.
        3. Set System properties using java -Dproperty=value.
        4. Include <property> elements in hibernate.cfg.xml (this is discussed later).
        Note: Configuration is intended as a startup-time object that will be discarded
         once a SessionFactory is created.

         SessionFactory sessions = cfg.buildSessionFactory();
         Session session = sessions.openSession(); // open a new Session
         Hibernate's own connection pooling algorithm is, however, quite rudimentary. It is intended to help you get
         started and is not intended for use in a production system, or even for performance testing.
         you might like to use c3p0.
        */
    }


    public static Configuration getAnnotatedConfiguration() {
        return new AnnotationConfiguration().configure().addAnnotatedClass(Employee.class);
    }

    public static StandardServiceRegistryBuilder getStandardServiceRegistryBuilder(Configuration configuration) {
        return new
                StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    }


    public static SessionFactory getSessionFactory(Configuration configuration, StandardServiceRegistryBuilder builder) {
        return configuration.buildSessionFactory(builder.build());
    }


}
