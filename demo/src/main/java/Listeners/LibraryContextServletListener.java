package Listeners;

import model.Book;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@WebListener()
public class LibraryContextServletListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public void contextInitialized(ServletContextEvent s)
    {
        ArrayList books = new ArrayList();

        books.add(new Book("Lzy diabla", "Magdalena Kozak", 2021));
        books.add(new Book("Nikopolis 1396", "Stanislaw Rek", 2021));
        books.add(new Book("Rebeka", "Daphne du Maurier", 2021));
        books.add(new Book("Afekt. Seria z Joanna Chylka. Tom 13", "Remigiusz Mroz", 2021));
        books.add(new Book("Pozniej", "Stephen King", 2021));
        books.add(new Book("Niewidzialne zycie Addie LaRue", "Victoria Schwab", 2021));

        s.getServletContext().setAttribute("books", books);
    }
}
