import Response.ServerResponse;
import model.Book;
import model.StatusCode;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteBooksServlet", value = "/book/*")
public class DeleteBooksServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            String[] routeParts = request.getPathInfo().split("/");

            if(routeParts.length != 2) {
                ServerResponse.writeResponse(response, StatusCode.NotFound, null);
                return;
            }

            Integer id = 0;
            try {
                id = Integer.parseInt(routeParts[1]);
            }
            catch (NumberFormatException exc) {
                id = null;
            }

            if(id == null) {
                ServerResponse.writeResponse(response, StatusCode.NotFound, null);
                return;
            }

            ServletContext context = getServletContext();

            List<Book> books = (List<Book>)context.getAttribute("books");

            Integer finalId = id;
            Book toDelete = books.stream().filter(b -> b.getId() == finalId).findFirst().orElse(null);

            if(toDelete == null) {
                ServerResponse.writeResponse(response, StatusCode.NotFound, "Ksiazka z id " + id + " nie istnieje w bazie.");
                return;
            }

            books.remove(toDelete);
            context.setAttribute("books", books);
            ServerResponse.writeResponse(response, StatusCode.OK, null);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, exc.getLocalizedMessage());
        }
    }
}
