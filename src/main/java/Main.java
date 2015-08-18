import com.app.rquispe.domain.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Employee employee = new Employee();
        //employee.setId(1);
        employee.setFirstName("Ruben");
        employee.setLastName("Quispe");
        employee.setSalary(10000.00);
        employee.setOptional("this will not persist");
        employee.setJoinDate(new Date());

        //create session factory only once
        SessionFactory sessionFactory =  new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        //session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();

        employee = null;

        session = sessionFactory.openSession();
        session.beginTransaction();
        //retrieve objects of this Employee class with primary key 1
        employee = (Employee)session.get(Employee.class, 1);

    }
}
