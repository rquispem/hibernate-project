package com.app.rquispe;

import com.app.rquispe.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by ruben on 8/15/15.
 */
public class HibernateAnnotationMain {

    public static void main(String[] args) {

        Configuration configuration = HibernateUtil.getAnnotatedConfiguration();

        StandardServiceRegistryBuilder builder =
                HibernateUtil.getStandardServiceRegistryBuilder(configuration);

        SessionFactory sessionFactory =
                HibernateUtil.getSessionFactory(configuration, builder);


        //Used to get a physical connection with a database.
        Session session =sessionFactory.openSession();
    }
}
