package Listeners;

import model.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

@WebListener()
public class UsersContextServletListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public UsersContextServletListener()
    {}

    public void contextInitialized(ServletContextEvent sce)
    {
        HashMap<String, User> users = new HashMap<>();

        users.put("adam", new User("adam", "adam"));
        users.put("marek", new User("marek", "marek"));
        users.put("tomek", new User("tomek", "tomek"));
        users.put("franek", new User("franek", "franek"));
        users.put("arek", new User("arek", "arek"));

        sce.getServletContext().setAttribute("users", users);
    }

    public void contextDestroyed(ServletContextEvent sce) {}
    public void sessionCreated(HttpSessionEvent se) {}

    public void sessionDestroyed(HttpSessionEvent se) {}

    public void attributeAdded(HttpSessionBindingEvent sbe) {}

    public void attributeRemoved(HttpSessionBindingEvent sbe) {}

    public void attributeReplaced(HttpSessionBindingEvent sbe) {}
}
