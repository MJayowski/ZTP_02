import Requests.BookRequest;
import Response.ResponseHandler;
import Response.ResultsOfValidation;
import Response.ServerResponse;
import com.google.gson.Gson;
import model.Book;
import model.StatusCode;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookDashboardServlet", value = "/book")
public class BookDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("books.size()");
        try {
            ServletContext context = getServletContext();
            List<Book> books = (List<Book>)context.getAttribute("books");
            ServerResponse.writeResponse(response, StatusCode.OK, books);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, exc.getLocalizedMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BookRequest bookreq = parseRequestBook(request, BookRequest.class);

            ResultsOfValidation validationResult = ValidateBook(bookreq);

            if(!validationResult.isValid()) {
                ServerResponse.writeResponse(response, StatusCode.BadRequest, validationResult.getErrors());
                return;
            }

            ServletContext context = getServletContext();
            List<Book> books = (List<Book>)context.getAttribute("books");
            Book newBook = new Book(bookreq.getTitle(), bookreq.getAuthor(), bookreq.getYear());
            books.add(newBook);
            context.setAttribute("books", books);

            ServerResponse.writeResponse(response, StatusCode.Created, newBook);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, exc.getLocalizedMessage());
        }
    }

    public static ResultsOfValidation ValidateBook(BookRequest bookreq)
    {
        ArrayList<String> errors = new ArrayList<>();

        if(bookreq == null || bookreq.getTitle() == null || "".equals(bookreq.getTitle())) {
            errors.add("Pole Tytul nie moze byc puste");
        }

        if(bookreq == null || bookreq.getAuthor() == null || "".equals(bookreq.getAuthor())) {
            errors.add("Pole Autor nie moze byc puste");
        }

        if(bookreq == null || bookreq.getYear() == null || bookreq.getYear() < 0 || bookreq.getYear() > 9999) {
            errors.add("Pole Rok nie moze byc puste i zawierać się w zakresie do 9999");
        }

        String[] arr = new String[errors.size()];
        return new ResultsOfValidation(errors.toArray(arr));
    }

    protected <T> T parseRequestBook(HttpServletRequest request, Class<T> tClass) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(request.getReader(), tClass);
    }
}
