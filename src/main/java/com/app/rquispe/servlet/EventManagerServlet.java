package com.app.rquispe.servlet;

import com.app.rquispe.domain.Event;
import com.app.rquispe.utils.HibernateUtil;
import com.app.rquispe.utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ruben on 8/15/15.
 */
public class EventManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            /*
             *Do not use a new Hibernate Session for every database operation.
             *  Use one Hibernate Session that is scoped to the whole request.
             *  Session represents a single-threaded unit of work.
             *  SessionFactory is a thread-safe global object that is instantiated once.
             *  Session is designed to represent a single unit of work
             *  (a single atomic piece of work to be performed).
             *
             *  The getCurrentSession() method always returns the "current" unit of work.
             *  he context of a current unit of work is bound to the current Java thread
             *  that executes the application.
             *
             *  Hibernate offers three methods of current session tracking. The "thread" based method is not
             *  intended for production use; it is merely useful for prototyping and tutorials such as this one.
             *
             *  Session begins when the first call to getCurrentSession() is made for the current thread.
             *  It is then bound by Hibernate to the current thread. When the transaction ends, either
             *  through commit or rollback, Hibernate automatically unbinds the org.hibernate.Session
             *  from the thread and closes it for you. If you call getCurrentSession() again, you get a
             *  new org.hibernate.Session and can start a new unit of work.
             *
             *  The scope of a Session is flexible but you should never design your
             *  application to use a new Hibernate Session for every database operation.
             *
             *  Do not use a new Hibernate Session for every database operation. Use one Hibernate Session that is
             *  scoped to the whole request. Use getCurrentSession(), so that it is automatically bound to the
             *  current Java thread.
            */

            // Begin unit of work
            HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();

            // Process request and render page...

            // End unit of work
            HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();

        }catch (Exception ex) {
            HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if (ServletException.class.isInstance(ex)) {
                throw (ServletException) ex;
            } else {
                throw new ServletException(ex);
            }

        }  // Write HTML header
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Event Manager</title></head><body>");

            // Handle actions
            SimpleDateFormat dateFormatter = new SimpleDateFormat();
            if ( "store".equals(request.getParameter("action")) ) {

                String eventTitle = request.getParameter("eventTitle");
                String eventDate = request.getParameter("eventDate");

                if ( "".equals(eventTitle) || "".equals(eventDate) ) {
                    out.println("<b><i>Please enter event title and date.</i></b>");
                }
                else {
                    try {
                        createAndStoreEvent(eventTitle, dateFormatter.parse(eventDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    out.println("<b><i>Added event.</i></b>");
                }
            }

            // Print page
            printEventForm(out);
            listEvents(out, dateFormatter);

            // Write HTML footer
            out.println("</body></html>");
            out.flush();
            out.close();
        }


    private void printEventForm(PrintWriter out) {
        out.println("<h2>Add new event:</h2>");
        out.println("<form>");
        out.println("Title: <input name='eventTitle' length='50'/><br/>");
        out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
        out.println("<input type='submit' name='action' value='store'/>");
        out.println("</form>");
    }

    private void listEvents(PrintWriter out, SimpleDateFormat dateFormatter) {

        List result = HibernateUtils.getSessionFactory()
                .getCurrentSession().createCriteria(Event.class).list();
        if (result.size() > 0) {
            out.println("<h2>Events in database:</h2>");
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>Event title</th>");
            out.println("<th>Event date</th>");
            out.println("</tr>");
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Event event = (Event) it.next();
                out.println("<tr>");
                out.println("<td>" + event.getTitle() + "</td>");
                out.println("<td>" + dateFormatter.format(event.getDate()) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }

    protected void createAndStoreEvent(String title, Date theDate) {
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);

        HibernateUtils.getSessionFactory()
                .getCurrentSession().save(theEvent);
    }
}
