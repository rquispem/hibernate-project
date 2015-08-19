package com.app.rquispe;

import com.app.rquispe.domain.Event;
import com.app.rquispe.domain.Person;
import com.app.rquispe.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;

/**
 * Created by ruben on 8/14/15.
 */
public class HibernateMain {

    /*Implementing a Naming Strategy
     *org.hibernate.cfg.NamingStrategy and org.hibernate.cfg.naming.NamingStrategyDelegator interfaces allow you
     * to specify a "naming standard" for database objects and schema elements.
     * You can provide rules for automatically generating database identifiers from Java identifiers or for processing
     * "logical" column and table names given in the mapping file into "physical" table and column names. This feature
     * helps reduce the verbosity of the mapping document, eliminating repetitive noise (TBL_ prefixes, for example).
     * The default strategy used by Hibernate is quite minimal.
     * new Configuration().setNamingStrategyDelegator(ImprovedNamingStrategyDelegator.DEFAULT_INSTANCE)
     */
    public static void main(String[] args) {

        Configuration configuration = HibernateUtil.getConfiguration();

        StandardServiceRegistryBuilder builder =
                HibernateUtil.getStandardServiceRegistryBuilder(configuration);

        SessionFactory sessionFactory =
                HibernateUtil.getSessionFactory(configuration, builder);


        //Used to get a physical connection with a database.
        Session session =sessionFactory.openSession();

        //Create a new Event
        Event event = new Event();
        event.setTitle("Repaso de Spring");
        event.setDate(new Date());

        //createEvent(session,event);
        List<Event> eventList = listEvents(session);

        //showListEvent(eventList);

        Person person = new Person();
        person.setFirstname("Ruben");
        person.setLastname("Quispe");
        person.setAge(26);
        createPerson(session, person);

        addPersonToEvent(1l,1l,session);

    }


    private static void createEvent(Session session, Event event) {
        session.beginTransaction();
        session.save(event);
        session.getTransaction().commit();
    }

    private static void createPerson(Session session, Person person) {
        session.beginTransaction();
        session.save(person);
        session.getTransaction().commit();
    }

    private static void addPersonToEvent(Long personId, Long eventId, Session session) {
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);
        //automatic dirty checking. save in database
        //aPerson.getEvents().add(anEvent);
        aPerson.addToEvent(anEvent);  //because getEvents is now protected

        session.getTransaction().commit();
    }

    private static void addEmailToPerson(Long personId, String emailAddress, Session session) {
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        // adding to the emailAddress collection might trigger a lazy load of the collection
        aPerson.getEmailAddresses().add(emailAddress);

        session.getTransaction().commit();
    }

    /*
     * You can load person and event in different units of work.
     */
    private void addPersonToEvent2(Long personId, Long eventId, Session session) {

        session.beginTransaction();

        Person aPerson = (Person) session
                .createQuery("select p from Person p left join fetch p.events where p.id = :pid")
                .setParameter("pid", personId)
                .uniqueResult(); // Eager fetch the collection so we can use it detached
        Event anEvent = (Event) session.load(Event.class, eventId);

        session.getTransaction().commit();

        // End of first unit of work

        //aPerson.getEvents().add(anEvent); // aPerson (and its collection) is detached
        aPerson.addToEvent(anEvent);

        // Begin second unit of work

        Session session2 = session;
        session2.beginTransaction();

        /*The call to update makes a detached object persistent again by binding it
         to a new unit of work, so any modifications you made to it while detached can be saved to the database.*/
        session2.update(aPerson); // Reattachment of aPerson

        session2.getTransaction().commit();
    }

    private static  List listEvents(Session session) {
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }

    private static void showListEvent(List<Event> eventList) {
        if(null != eventList && eventList.size() > 0)
        {
            for(Event ev : eventList)
            {
                System.out.println(ev.getId() + ", " + ev.getTitle() + ", " + ev.getDate());
            }
        }
    }


}
