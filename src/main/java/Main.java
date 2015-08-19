import com.app.rquispe.domain.Address;
import com.app.rquispe.domain.Employee;
import com.app.rquispe.domain.Project;
import com.app.rquispe.domain.Vehicle;
import com.app.rquispe.utils.HibernateUtil;
import com.app.rquispe.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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

        Address address1 = new Address();
        address1.setCity("Lima");
        address1.setPincode("123");
        address1.setState("lima");
        address1.setStreet("rimac");

        Address address2 = new Address();
        address2.setCity("Trujillo");
        address2.setPincode("456");
        address2.setState("trujillo");
        address2.setStreet("golf");

        employee.getListOfAddresses().add(address1);
        employee.getListOfAddresses().add(address2);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName("Car");
        employee.setVehicle(vehicle);

        Project project1 = new Project();
        project1.setName("Baynote");
        project1.setDescription("Image server project");

        Project project2 = new Project();
        project2.setName("AOL");
        project2.setDescription("Excel project");

        employee.getProjects().add(project1);
        employee.getProjects().add(project2);
        //create session factory only once
       //SessionFactory sessionFactory =  new Configuration().configure().buildSessionFactory();
        Configuration configuration = HibernateUtil.getConfiguration();

        StandardServiceRegistryBuilder builder =
                HibernateUtil.getStandardServiceRegistryBuilder(configuration);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(configuration, builder);

        Session session = sessionFactory.openSession();
        //session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(employee);
        session.save(vehicle);
        session.getTransaction().commit();
        session.close();

        employee = null;

        session = sessionFactory.openSession();
        session.beginTransaction();
        //retrieve objects of this Employee class with primary key 1
        employee = (Employee)session.get(Employee.class, 1);
        session.close();
        employee.getListOfAddresses().size(); // by default use lazy initialization



    }
}
